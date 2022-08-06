package com.dominic.coin_search.feature_coins.presentation.coins_screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominic.coin_search.core.util.Constants.UPDATE_INTERVAL
import com.dominic.coin_search.core.util.Constants.VISIBLE_ITEM_COUNT
import com.dominic.coin_search.feature_coins.domain.exceptions.CoinExceptions
import com.dominic.coin_search.feature_coins.domain.models.ChartModel
import com.dominic.coin_search.feature_coins.domain.models.ChartTimeSpan
import com.dominic.coin_search.feature_coins.domain.models.CoinCurrencyPreference
import com.dominic.coin_search.feature_coins.domain.models.CoinModel
import com.dominic.coin_search.feature_coins.domain.use_case.CoinUseCases
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

    private val _coinChart = mutableStateListOf<ChartModel>()
    val coinChart = _coinChart
    private var job: Job? = null

    init {
         loadInformation(onCurrencyCollected = ::subscribeToCoinChanges).also{ job ->
             job.invokeOnCompletion {
                 _state.update { it.copy(isLoading = false) }
             }
         }
    }

    private fun subscribeToCoinChanges(currency:String){
        job = viewModelScope.launch(Dispatchers.IO) {
            while(isActive){
                getCoins(currency)
                delay(UPDATE_INTERVAL)
            }
        }
    }
    private fun unSubscribeToCoinChanges(){
        job?.cancel()
    }


    private fun loadInformation(onCurrencyCollected: (String) -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            getGlobalMarket()
            getCoinCurrencies()
            getCurrency(onCurrencyCollected = { coinCurrencyPreference ->
                onCurrencyCollected(coinCurrencyPreference.currency!!)
                getCoins(coinCurrencyPreference.currency , onCoinsCollected = { coinModels ->
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
        currency: String?,
        onCoinsCollected: suspend (List<CoinModel>) -> Unit = {}) {
        coroutineScope {
            runCatching {
                coinUseCase.getCoins(currency?:"USD")
                    .distinctUntilChanged().collect { coins ->
                        _state.update {
                            it.copy(coinModels = coins)
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
                    coinUseCase.getChart(coinId = coin.id, period = ChartTimeSpan.OneDay.value).toList(_coinChart)
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
        _state.update{it.copy(coinCurrencyPreference = coinCurrencyPreference)}

    }

    fun onEvent(event: CoinsEvent) {
        when (event) {
            is CoinsEvent.RefreshCoins -> {
                viewModelScope.launch {
                    _state.update { it.copy(isRefreshing = true) }
                     getCoins(event.coinCurrencyPreference.currency)
                }.invokeOnCompletion {
                    _state.update { it.copy(isRefreshing = false) }
                }
            }

            is CoinsEvent.CloseNoInternetDisplay -> {
                _state.update { it.copy(hasInternet = true) }
            }

            is CoinsEvent.SelectCurrency -> {

                viewModelScope.launch {

                    _state.update { it.copy(isLoading = true) }
                    withContext(Dispatchers.IO) {
                        getCoins(event.coinCurrencyPreference.currency){
                            updateCoinCurrency(event.coinCurrencyPreference)
                        }
                    }

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