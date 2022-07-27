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

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val coinUseCase: CoinUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(CoinsState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        val infoJob: Job = loadInformation().also { job ->
            job.invokeOnCompletion {
                _state.update { it.copy(isLoading = false) }
            }
        }

        if(infoJob.isActive){
            subscribeToCoinChanges()
        }
    }

    private fun subscribeToCoinChanges(){
        job = viewModelScope.launch(Dispatchers.IO) {
            while(isActive){
                getCoins(state.value.coinCurrencyPreference)
                delay(UPDATE_INTERVAL)
            }
        }
    }
    private fun unSubscribeToCoinChanges(){
        job?.cancel()
    }


    private fun loadInformation() =
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            getGlobalMarket()
            getCoinCurrencies()
            getCurrency(onCurrencyCollected = { coinCurrencyPreference ->
                getCoins(coinCurrencyPreference, onCoinsCollected = { coinModels ->
                    getChart(coinModels)
                })
            })

        }


    private suspend fun getCurrency(onCurrencyCollected: suspend (CoinCurrencyPreference) -> Unit = {}) {
        coroutineScope {
            runCatching {
                coinUseCase.getCurrency().first()
            }.onSuccess { coinCurrencyPreference ->
                _state.update { it.copy(coinCurrencyPreference = coinCurrencyPreference) }
                onCurrencyCollected(coinCurrencyPreference)
            }.onFailure { exception ->
                handleException(exception)
            }
        }
    }

    private suspend fun getGlobalMarket() {
        coroutineScope {
            runCatching {
                coinUseCase.getGlobalMarket()
            }.onSuccess { globalMarket ->
                _state.update { it.copy(globalMarket = globalMarket, tickerVisible = true) }
            }.onFailure { exception ->
                handleException(exception)
            }
        }
    }

    private suspend fun getCoins(
        coinCurrencyPreference: CoinCurrencyPreference,
        onCoinsCollected: suspend (List<CoinModel>) -> Unit = {}) {
        coroutineScope {
            runCatching {
                coinUseCase.getCoins(coinCurrencyPreference.currency ?: "USD")
                    .distinctUntilChanged().collect { coins ->
                        _state.update {
                            it.copy(
                                coinModels = coins,
                                coinCurrencyPreference = coinCurrencyPreference)
                        }
                        onCoinsCollected(coins)
                    }
            }.onFailure { exception ->
                handleException(exception)
            }
        }
    }

    private suspend fun getChart(coinModels: List<CoinModel>) {
        coroutineScope {
            coinModels.forEach { coin ->
                runCatching {
                    coinUseCase.getChart(coinId = coin.id, period = "24h").toList(state.value.chart)
                }.onSuccess { chartModels ->
                    val isItemsRendered = chartModels.size > VISIBLE_ITEM_COUNT

                    _state.update {
                        it.copy(isLoading = !isItemsRendered, isItemsRendered = isItemsRendered)
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

    private suspend fun getCoinCurrencies() {
        coroutineScope {
            runCatching {
                coinUseCase.getFiats().currencies
            }.onSuccess { currencies ->
                _state.value = state.value.copy(currencies = currencies)
            }.onFailure { exception ->
                handleException(exception)
            }
        }
    }

    private suspend fun updateCoinCurrency(coinCurrencyPreference: CoinCurrencyPreference) {
        coinUseCase.updateCurrency(coinCurrencyPreference)
    }

    fun onEvent(event: CoinsEvent) {
        when (event) {
            is CoinsEvent.RefreshCoins -> {
                viewModelScope.launch {
                    _state.update { it.copy(isRefreshing = true) }
                    event.coinCurrencyPreference?.let {
                        getCoins(it)
                    }
                }.invokeOnCompletion {
                    _state.update { it.copy(isRefreshing = false) }
                }
            }

            is CoinsEvent.CloseNoInternetDisplay -> {
                _state.update { it.copy(hasInternet = true) }
            }

            is CoinsEvent.EnteredCoinsSearchQuery -> {
                _state.update { it.copy(searchCoinsQuery = event.searchQuery) }
            }




            is CoinsEvent.SelectCurrency -> {

                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }
                    updateCoinCurrency(event.coinCurrencyPreference)
                    getCoins(event.coinCurrencyPreference)

                }.invokeOnCompletion {
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }

    }


    override fun onCleared() {
        super.onCleared()
        unSubscribeToCoinChanges()
    }
}