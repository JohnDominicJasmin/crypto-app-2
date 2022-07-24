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
            delay(700)
            getCoins(state.value.coinCurrencyPreference)
            delay(1000)
            getChart(state.value.coinModels)

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

    private fun getCoins(coinCurrencyPreference: CoinCurrencyPreference?) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                while (isActive) {
                    coinUseCase.getCoins(coinCurrencyPreference?.currency ?: "USD").distinctUntilChanged().collect { coins ->
                        _state.update { it.copy(coinModels = coins, coinCurrencyPreference = coinCurrencyPreference, isLoading = false) }
                    }
                    delay(UPDATE_INTERVAL)
                }
            }.onFailure { exception ->
                handleException(exception)
            }
        }
    }

    private fun getChart(coinModels: List<CoinModel>){
        viewModelScope.launch(Dispatchers.IO) {
        coinModels.forEach { coin ->
            runCatching {
                coinUseCase.getChart(coinId = coin.id, period = "24h").toList(state.value.chart)
            }.onSuccess { chartModels ->
                val isItemsRendered = chartModels.size > VISIBLE_ITEM_COUNT
                _state.update {
                    it.copy(isLoading = !isItemsRendered, isItemsRendered = isItemsRendered, isRefreshing = false)
                }
            }.onFailure { exception ->
                handleException(exception)
            }
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
    }
    fun onEvent(event: CoinsEvent) {
        when (event) {
            is CoinsEvent.RefreshCoins -> {
                _state.update { it.copy(isRefreshing = true) }
                getCoins(event.coinCurrencyPreference)
            }

            is CoinsEvent.CloseNoInternetDisplay -> {
                _state.update { it.copy(hasInternet = true) }
            }

            is CoinsEvent.EnteredSearchQuery -> {
                _state.update { it.copy(searchQuery = event.searchQuery) }
            }

            is CoinsEvent.SelectCurrency -> {

                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }
                    updateCoinCurrency(event.coinCurrencyPreference)
                    getCoins(event.coinCurrencyPreference)

                }
            }
        }

    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}