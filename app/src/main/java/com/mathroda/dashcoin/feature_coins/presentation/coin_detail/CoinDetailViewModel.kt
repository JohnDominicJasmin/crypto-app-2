package com.mathroda.dashcoin.feature_coins.presentation.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathroda.dashcoin.core.util.Constants
import com.mathroda.dashcoin.core.util.Constants.UPDATE_INTERVAL
import com.mathroda.dashcoin.feature_coins.domain.exceptions.CoinExceptions
import com.mathroda.dashcoin.feature_coins.domain.models.ChartTimeSpan
import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel
import com.mathroda.dashcoin.feature_coins.domain.use_case.CoinUseCases
import com.mathroda.dashcoin.feature_favorite_list.domain.use_case.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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
        loadCoinDetail(onCollectedChartPeriod = { coinId ->
            subscribeToChartChanges(coinId)
            subscribeToCoinChanges(coinId)
        })
    }

    private fun loadCoinDetail(onCollectedChartPeriod: (String) -> Unit = { _ -> }) {
        viewModelScope.launch(Dispatchers.IO) {

            savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->

                _state.update { it.copy(coinId = coinId) }
                getCoin(coinId)
                getChartPeriod() { chartPeriod ->
                    getChart(coinId = coinId, period = chartPeriod)
                    onCollectedChartPeriod(coinId)
                }

            }
        }
    }


    private suspend fun getChartPeriod(onChartPeriodCollected: suspend (String) -> Unit = {}) {
        runCatching {
            val chartPeriod = coinUseCase.getChartPeriodUseCase().distinctUntilChanged().first()
            val resultPeriod = chartPeriod ?: ChartTimeSpan.OneDay.value
            onChartPeriodCollected(resultPeriod)
            _state.update { it.copy(coinChartPeriod = resultPeriod) }
        }.onFailure { exception ->
            handleException(exception)
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


    private suspend fun isFavoriteCoin(coinDetailModel: CoinDetailModel): Boolean {
        val coins: List<CoinDetailModel> =
            favoriteUseCase.getAllCoins().distinctUntilChanged().first()

        return suspendCoroutine { continuation ->
            continuation.resume(coins.any { it.name == coinDetailModel.name })
        }
    }


    private fun subscribeToCoinChanges(coinId: String) {
        coinChangesJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            while (isActive) {
                getCoin(coinId = coinId)
                delay(UPDATE_INTERVAL)
            }
        }
    }

    private fun subscribeToChartChanges(coinId: String) {
        coinChartJob = viewModelScope.launch {
            while (isActive) {
                getChart(coinId = coinId, period = state.value.coinChartPeriod)
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


    private suspend fun getCoin(coinId: String) {

        runCatching {
            coinUseCase.getCoin(coinId, currency = "USD")
                .distinctUntilChanged().collect { coinDetail ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            coinDetailModel = coinDetail,
                            isFavorite = isFavoriteCoin(coinDetail)
                        )
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
                coinUseCase.getChart(coinId, period = period).distinctUntilChanged()
                    .collect { chartModel ->
                        _state.update { it.copy(chartModel = chartModel) }
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