package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathroda.dashcoin.core.util.Constants.VISIBLE_ITEM_COUNT
import com.mathroda.dashcoin.feature_coins.domain.exceptions.CoinExceptions
import com.mathroda.dashcoin.feature_coins.domain.use_case.CoinUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val coinUseCase: CoinUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(CoinsState())
    val state = _state.asStateFlow()


    init {
        getGlobalMarket()
        getCoins()
    }


    private fun getGlobalMarket() {
        viewModelScope.launch {
            runCatching {
                  coinUseCase.getGlobalMarket()
            }.onSuccess { globalMarket ->
                _state.update { it.copy(globalMarket = globalMarket, tickerVisible = true, isLoading = false) }
            }.onFailure { exception ->
                handleException(exception)
            }
        }
    }

    private fun getCoins() {

        viewModelScope.launch {
            runCatching {

                while (isActive) {
                    coinUseCase.getCoins().distinctUntilChanged().collect { coins ->
                        _state.update { it.copy(coin = coins) }
                        coins.forEach { coin ->
                            coinUseCase.getChart(coinId = coin.id, period = "24h").toList(state.value.chart)
                            val isItemsRendered = state.value.chart.size > VISIBLE_ITEM_COUNT
                            _state.update {
                                it.copy(isLoading = !isItemsRendered, isItemsRendered = isItemsRendered, isRefreshing = false)
                            }
                        }
                    }
                    delay(30.seconds)
                }


            }.onFailure { exception ->
                handleException(exception)
            }
        }
    }

    private suspend fun handleException(exception: Throwable) {
        coroutineScope {
            _state.update { it.copy(isLoading = false, isRefreshing = false,) }
            when (exception) {
                is CoinExceptions.UnexpectedErrorException -> {
                    _state.update { it.copy(errorMessage = exception.message!!) }
                }
                is CoinExceptions.NoInternetException -> {
                    _state.update { it.copy(hasInternet = false,) }
                }
            }
            this.cancel()
        }
    }

    fun onEvent(event: CoinsEvent) {
        when (event) {
            is CoinsEvent.RefreshCoins -> {
                _state.update { it.copy(isRefreshing = true) }
                getCoins()
                getGlobalMarket()
            }

            is CoinsEvent.CloseNoInternetDisplay -> {
                _state.update { it.copy(hasInternet = true) }
            }

            is CoinsEvent.EnteredSearchQuery -> {
                _state.update { it.copy(searchQuery = event.searchQuery) }
            }
        }
    }





}