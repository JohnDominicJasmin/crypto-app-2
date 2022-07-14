package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.charts.LineChart
import com.mathroda.dashcoin.feature_coins.domain.exceptions.CoinExceptions
import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel
import com.mathroda.dashcoin.feature_coins.domain.use_case.CoinUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val coinUseCase: CoinUseCases
): ViewModel() {

    private val _state = MutableStateFlow(CoinsState())
    val state = _state.asStateFlow()


    init {
        viewModelScope.launch{
            getCoins()

        }
    }


    private suspend fun getCoins() {

        coroutineScope {
            runCatching {
                _state.update { it.copy(isLoading = true) }
                withContext(Dispatchers.IO) {
                    coinUseCase.getCoins().collect { coins ->
                        _state.update { it.copy(coinModels = coins) }
                        _state.update { it.copy(chartModels = getCharts(coins)) }
                        }
                }


            }.onFailure { exception ->

                    when (exception) {
                        is CoinExceptions.UnexpectedErrorException -> {
                            _state.update { it.copy(errorMessage = exception.message!!) }
                        }
                        is CoinExceptions.NoInternetException -> {
                            _state.update { it.copy(hasInternet = false) }
                        }
                    }

                this.cancel()

            }.also{
                _state.update { it.copy(isLoading = false) }
            }
        }
    }


    private suspend fun getCharts(coins: List<CoinModel>): List<ChartModel> =
        mutableListOf<Deferred<ChartModel>>().run {
            coroutineScope {
                coins.forEach { coin ->
                    val chart = async {
                        coinUseCase.getChart(coinId = coin.id).first()
                    }
                    add(chart)
                }
            }
            return awaitAll()
}

    fun onEvent(event: CoinsEvent){
        when(event){
            is CoinsEvent.RefreshCoins -> {
                refresh()
            }

            is CoinsEvent.CloseNoInternetDisplay -> {
                _state.update { it.copy(hasInternet = true, isRefreshing = false) }
                refresh()
            }

            is CoinsEvent.EnteredSearchQuery -> {

                _state.update { it.copy(searchQuery = event.searchQuery) }
            }
        }
    }
    private fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            getCoins()
            _state.update { it.copy(isRefreshing = false) }
        }

    }


}