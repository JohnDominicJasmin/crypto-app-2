package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathroda.dashcoin.feature_coins.domain.exceptions.CoinExceptions
import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel
import com.mathroda.dashcoin.feature_coins.domain.use_case.CoinUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val coinUseCase: CoinUseCases
): ViewModel() {

    private val _state = MutableStateFlow(CoinsState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<CoinsUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch{
            _state.update { it.copy(isLoading = true) }
            getCoins()
        }
    }


    private suspend fun getCoins() {

        coroutineScope {
            runCatching {

                withContext(Dispatchers.IO) {
                    while(isActive) {
                        coinUseCase.getCoins().distinctUntilChanged().collect { coins ->
                            _state.update { it.copy(coinModels = coins) }
                            _state.update { it.copy(chartModels = getCharts(coins), isLoading = false, isRefreshing = false)}
                        }
                        delay(30.seconds)
                    }
                }


            }.onFailure { exception ->
                    _state.update { it.copy(isLoading = false, isRefreshing = false)}
                    when (exception) {
                        is CoinExceptions.UnexpectedErrorException -> {
                            _state.update { it.copy(errorMessage = exception.message!!) }
                        }
                        is CoinExceptions.NoInternetException -> {
                            _eventFlow.emit(CoinsUiEvent.ShowToastMessage(message = exception.message!!))
                        }
                    }

                this.cancel()

            }
        }
    }


    private suspend fun getCharts(coins: List<CoinModel>): List<ChartModel> =
        mutableListOf<Deferred<ChartModel>>().run {
            coroutineScope {
                coins.forEach { coin ->
                    val chart = async {
                        coinUseCase.getChart(coinId = coin.id, period = "24h").first()
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

            is CoinsEvent.EnteredSearchQuery -> {

                _state.update { it.copy(searchQuery = event.searchQuery) }
            }
        }
    }
    private fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            getCoins()
        }

    }


}