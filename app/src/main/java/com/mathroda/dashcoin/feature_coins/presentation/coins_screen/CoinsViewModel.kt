package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathroda.dashcoin.core.util.Constants.UPDATE_INTERVAL
import com.mathroda.dashcoin.core.util.Constants.VISIBLE_ITEM_COUNT
import com.mathroda.dashcoin.feature_coins.domain.exceptions.CoinExceptions
import com.mathroda.dashcoin.feature_coins.domain.models.CoinCurrencyPreference
import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel
import com.mathroda.dashcoin.feature_coins.domain.use_case.CoinUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val coinUseCase: CoinUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(CoinsState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        viewModelScope.launch {
            getCurrency()
            getGlobalMarket()
            getCoinCurrencies()
            delay(500)
            getCoins(_state.value.coinCurrencyPreference?.currency)
        }
    }

    private fun getCurrency(){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                coinUseCase.getCurrency().first()
            }.onSuccess { coinCurrencyPreference ->
                _state.update { it.copy(coinCurrencyPreference = coinCurrencyPreference) }
            }.onFailure { exception ->
                handleException(exception)
            }.also {
                this.cancel()
            }
        }
    }

    private fun getGlobalMarket() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                  coinUseCase.getGlobalMarket()
            }.onSuccess { globalMarket ->
                _state.update { it.copy(globalMarket = globalMarket, tickerVisible = true, isLoading = false) }
            }.onFailure { exception ->
                handleException(exception)
            }
        }
    }

    private fun getCoins(currency: String?) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                while (isActive) {
                    coinUseCase.getCoins(currency ?: "USD").distinctUntilChanged().collect { coins ->
                        _state.update { it.copy(coinModels = coins) }
                        getChart(coins)//todo: remove getChart here
                    }
                    delay(UPDATE_INTERVAL)
                }
            }.onFailure { exception ->
                handleException(exception)
            }
        }
    }

    private suspend fun getChart(coinModels: List<CoinModel>){
        coinModels.forEach { coin ->
            coinUseCase.getChart(coinId = coin.id, period = "24h").toList(state.value.chart)
            val isItemsRendered = state.value.chart.size > VISIBLE_ITEM_COUNT
            _state.update {
                it.copy(isLoading = !isItemsRendered, isItemsRendered = isItemsRendered, isRefreshing = false)
            }
        }
    }
    private suspend fun handleException(exception: Throwable) {
        withContext(Dispatchers.Main) {
            _state.update { it.copy(isLoading = false, isRefreshing = false) }
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

    private fun getCoinCurrencies(){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                coinUseCase.getFiats().currencies
            }.onSuccess { currencies ->
                _state.value = state.value.copy(currencies = currencies)
            }.onFailure { exception ->
                handleException(exception)
            }
        }
    }

    private suspend fun updateCoinCurrency(coinCurrencyPreference: CoinCurrencyPreference){
        coinUseCase.updateCurrency(coinCurrencyPreference)
        _state.update { it.copy(coinCurrencyPreference = coinCurrencyPreference) }
    }
    fun onEvent(event: CoinsEvent) {
        when (event) {
            is CoinsEvent.RefreshCoins -> {
                _state.update { it.copy(isRefreshing = true) }
                getCoins(event.currency)
            }

            is CoinsEvent.CloseNoInternetDisplay -> {
                _state.update { it.copy(hasInternet = true) }
            }

            is CoinsEvent.EnteredSearchQuery -> {
                _state.update { it.copy(searchQuery = event.searchQuery) }
            }

            is CoinsEvent.SelectCurrency -> {
                _state.update { it.copy(isLoading = true) }
                viewModelScope.launch(Dispatchers.IO) {
                    updateCoinCurrency(event.coinCurrencyPreference)
                    getCoins(event.coinCurrencyPreference.currency)
                }
            }
        }

    }

//todo:
    // handle errors

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}