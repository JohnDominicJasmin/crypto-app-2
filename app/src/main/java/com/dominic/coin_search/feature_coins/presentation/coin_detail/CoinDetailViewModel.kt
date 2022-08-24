package com.dominic.coin_search.feature_coins.presentation.coin_detail


import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominic.coin_search.core.util.Constants
import com.dominic.coin_search.core.util.Constants.UPDATE_INTERVAL
import com.dominic.coin_search.feature_coins.domain.exceptions.CoinExceptions
import com.dominic.coin_search.feature_coins.domain.models.chart.ChartTimeSpan
import com.dominic.coin_search.feature_coins.domain.models.coin.CoinDetailModel
import com.dominic.coin_search.feature_coins.domain.use_case.CoinUseCases
import com.dominic.coin_search.feature_favorite_list.domain.use_case.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
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

    private suspend fun getCurrency() {
        runCatching {
            coinUseCase.getCurrency().first()
        }.onSuccess { coinCurrencyPreference ->
            _state.update { it.copy(coinCurrencyPreference = coinCurrencyPreference) }
        }.onFailure { exception ->
            Timber.e(exception.message)
        }
    }

    private suspend fun getExchangeRate() {
        runCatching {
            val currency = state.value.coinCurrencyPreference.currency?.uppercase()
            coinUseCase.getCurrencyExchangeRate(currency ?: "USD")
        }.onSuccess { currencyExchange ->
            _state.update { it.copy(currencyExchange = currencyExchange.result) }
        }.onFailure { exception ->
            handleException(exception)
        }
    }

    private fun loadCoinDetail(onCollectedChartPeriod: (String) -> Unit = { _ -> }) {
        viewModelScope.launch(Dispatchers.IO) {

            savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
                _state.update { it.copy(coinId = coinId, isLoading = true) }
                getCurrency()
                getCoin(coinId)
                getExchangeRate()
                getCoinInformation()
                getChartPeriod()
                getChart(coinId = coinId, period = state.value.coinChartPeriod)
                onCollectedChartPeriod(coinId)

            }
        }.invokeOnCompletion {
            _state.update { it.copy(isLoading = false) }
        }
    }


    private suspend fun getCoinInformation() {
        runCatching {
            with(state.value.coinDetailModel!!) {
                coinUseCase.getCoinInformation("${symbol.toLowerCase(Locale.current)}-$id")
            }
        }.onSuccess { coinInformation ->
            _state.update { it.copy(coinInformation = coinInformation) }
        }.onFailure { exception ->
            Timber.e(exception.message)
        }
    }


    private suspend fun getChartPeriod() {
        runCatching {
            coinUseCase.getChartPeriod().distinctUntilChanged().first()
        }.onSuccess { chartPeriod ->
            val resultPeriod = chartPeriod ?: ChartTimeSpan.OneDay.value
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

            is CoinDetailEvent.AddChartPrice -> {
                _state.update { it.copy(chartPrice = event.price) }
            }

            is CoinDetailEvent.AddChartDate -> {
                _state.update { it.copy(chartDate = event.date) }
            }

            is CoinDetailEvent.ClearChartDate -> {
                _state.update { it.copy(chartDate = "") }
            }

            is CoinDetailEvent.ClearChartPrice -> {
                _state.update { it.copy(chartPrice = 0.0) }

            }


        }

    }


    private suspend fun updateChartPeriod(period: String) {
        coinUseCase.updateChartPeriod(period)
    }


    private suspend fun isFavoriteCoin(coinName: String): Boolean {
        val coins: List<CoinDetailModel> =
            favoriteUseCase.getCoins().distinctUntilChanged().first()

        return suspendCoroutine { continuation ->
            continuation.resume(coins.any { it.name == coinName })
        }
    }


    private fun subscribeToCoinChanges(coinId: String) {
        coinChangesJob = viewModelScope.launch {
            while (isActive) {
                delay(UPDATE_INTERVAL)
                getCoin(coinId = coinId)
            }
        }
    }

    private fun subscribeToChartChanges(coinId: String) {
        coinChartJob = viewModelScope.launch {
            while (isActive) {
                delay(UPDATE_INTERVAL)
                getChart(coinId = coinId, period = state.value.coinChartPeriod)
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
            coinUseCase.getCoin(
                coinId,
                currency = state.value.coinCurrencyPreference.currency ?: "USD")
                .distinctUntilChanged().collect { coinDetail ->
                    _state.update {
                        it.copy(
                            coinDetailModel = coinDetail,
                            isFavorite = isFavoriteCoin(coinDetail.name)
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

