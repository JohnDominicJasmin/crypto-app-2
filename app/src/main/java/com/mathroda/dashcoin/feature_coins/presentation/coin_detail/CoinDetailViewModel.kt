package com.mathroda.dashcoin.feature_coins.presentation.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathroda.dashcoin.core.util.Constants
import com.mathroda.dashcoin.core.util.Constants.UPDATE_INTERVAL
import com.mathroda.dashcoin.feature_coins.domain.exceptions.CoinExceptions
import com.mathroda.dashcoin.feature_coins.domain.models.ChartTimeSpan
import com.mathroda.dashcoin.feature_coins.domain.models.CoinCurrencyPreference
import com.mathroda.dashcoin.feature_coins.domain.use_case.CoinUseCases
import com.mathroda.dashcoin.feature_favorite_list.domain.use_case.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinUseCase: CoinUseCases,
    private val favoriteUseCase: FavoriteUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _state = MutableStateFlow(CoinDetailState())
    val state = _state.asStateFlow()

    private var coinChangesJob: Job? = null
    private var coinChartJob: Job? = null

    init {
        loadCoinDetail()
    }

    private fun loadCoinDetail() {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
            _state.update { it.copy(coinId = coinId) }
            subscribeToCoinChanges(coinId)
            isFavoriteCoin()
            getChartPeriod() { chartPeriod ->
                getChart(coinId = coinId, period = chartPeriod)
                subscribeToChartChanges(coinId = coinId, period = chartPeriod)
            }
        }
    }


    private fun getChartPeriod(onChartPeriodCollected: suspend (String) -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                coinUseCase.getChartPeriodUseCase().distinctUntilChanged().collect { chartPeriod ->
                    onChartPeriodCollected(chartPeriod ?: ChartTimeSpan.OneDay.value)
                    _state.update {
                        it.copy(
                            coinChartPeriod = chartPeriod ?: ChartTimeSpan.OneDay.value)
                    }
                    this.cancel()
                }
            }.onFailure { exception ->
                handleException(exception)
            }
        }
    }


    fun onEvent(event: CoinDetailEvent) {
        when (event) {
            is CoinDetailEvent.CloseNoInternetDisplay -> {
                _state.update { it.copy(hasInternet = true) }
            }
            is CoinDetailEvent.ToggleFavoriteCoin -> {
                _state.update { it.copy(isFavorite = !_state.value.isFavorite) }
            }
            is CoinDetailEvent.LoadCoinDetail -> {
                loadCoinDetail()
            }

            is CoinDetailEvent.SelectChartPeriod -> {
                viewModelScope.launch {
                    _state.update { it.copy(coinChartPeriod = event.period, isLoading = true) }
                    withContext(Dispatchers.IO) {
                        updateChartPeriod(event.period)
                        getChart(coinId = state.value.coinId, period = event.period)
                    }
                }.invokeOnCompletion {
                    _state.update { it.copy(isLoading = false) }
                }
            }

            is CoinDetailEvent.ChangeYAxisValue -> {
                _state.update { it.copy(chartPrice = event.yValue) }
            }

            is CoinDetailEvent.ChangeXAxisValue -> {
                _state.update { it.copy(chartDate = event.xValue) }
            }
        }

    }


    private suspend fun updateChartPeriod(period: String) {
        coinUseCase.updateChartPeriodUseCase(period)
    }

    private fun isFavoriteCoin() {

        viewModelScope.launch(Dispatchers.IO) {
            favoriteUseCase.getAllCoins().distinctUntilChanged().cancellable().onEach { coins ->
                coins.any { it.name == _state.value.coinDetailModel?.name }
                    .let { isFavoriteCoin ->
                        _state.update { it.copy(isFavorite = isFavoriteCoin) }
                        this.cancel()
                    }
            }.launchIn(this)
        }

    }


    private fun subscribeToCoinChanges(coinId: String) {
        coinChangesJob = viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }
                while (isActive) {
                    val currencyPreference = coinUseCase.getCurrency().first()
                    getCoin(coinId = coinId, currencyPreference = currencyPreference)
                    delay(UPDATE_INTERVAL)
                }
        }
    }

    private fun subscribeToChartChanges(coinId: String, period: String) {
        coinChartJob = viewModelScope.launch {
            while (isActive){
                getChart(coinId = coinId, period = period)
                delay(UPDATE_INTERVAL)
            }
        }
    }

    private fun unSubscribeToCoinChanges() {
        coinChangesJob?.cancel()
    }
    private fun unSubscribeToChartChanges() {
        coinChartJob?.cancel()
    }



    private suspend fun getCoin(coinId: String, currencyPreference: CoinCurrencyPreference){

        runCatching {
            coinUseCase.getCoin(coinId, currency = currencyPreference.currency ?: "USD")
                .distinctUntilChanged().collect { coinDetail ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            coinDetailModel = coinDetail,
                            currencySymbol = currencyPreference.currencySymbol ?: "$")
                    }
                }
        }.onFailure { exception ->
            handleException(exception)
        }
    }


    private suspend fun handleException(exception: Throwable) {
        withContext(Dispatchers.Main) {
            _state.update { it.copy(isLoading = false) }
            when (exception) {
                is CoinExceptions.UnexpectedErrorException -> {
                    _state.update { it.copy(errorMessage = exception.message!!) }
                }
                is CoinExceptions.NoInternetException -> {
                    _state.update { it.copy(hasInternet = false) }
                }
            }
            this.cancel()
        }
    }

    private suspend fun getChart(coinId: String, period: String) {

        coroutineScope {
            runCatching {
                coinUseCase.getChart(coinId, period = period).collect { chartModel ->
                    _state.update {
                        it.copy(chartModel = chartModel)
                    }
                }
            }.onFailure { exception ->
                handleException(exception)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        unSubscribeToCoinChanges()
        unSubscribeToChartChanges()
    }
}