package com.mathroda.dashcoin.feature_coins.presentation.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathroda.dashcoin.core.util.Constants
import com.mathroda.dashcoin.core.util.Constants.UPDATE_INTERVAL
import com.mathroda.dashcoin.feature_coins.domain.exceptions.CoinExceptions
import com.mathroda.dashcoin.feature_coins.domain.models.ChartTimeSpan
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

    init {
        loadCoinDetail()
    }

    private fun loadCoinDetail() {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
            _state.update { it.copy(coinId = coinId) }
            getCoin(coinId)
            isFavoriteCoin()
            getChartPeriod(){ chartPeriod ->
                getChart(coinId = coinId, period = chartPeriod)
            }
        }
    }

    private fun getChartPeriod(onChartPeriodCollected: suspend (String) -> Unit = {} ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                coinUseCase.getChartPeriodUseCase().distinctUntilChanged().collect { chartPeriod ->
                    onChartPeriodCollected(chartPeriod ?: ChartTimeSpan.OneDay.value)
                    _state.update { it.copy(coinChartPeriod = chartPeriod ?: ChartTimeSpan.OneDay.value) }
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
                    _state.update { it.copy(coinChartPeriod = event.period) }
                    withContext(Dispatchers.IO) {
                        updateChartPeriod(event.period)
                        getChart(coinId = state.value.coinId, period = event.period)
                    }
                }
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


    private fun getCoin(coinId: String) {
        viewModelScope.launch {
            runCatching {
                _state.update { it.copy(isLoading = true) }
                while (isActive) {
                    coinUseCase.getCoin(coinId).distinctUntilChanged().collect { coinDetail ->
                        _state.update { it.copy(isLoading = false, coinDetailModel = coinDetail) }
                    }
                    delay(UPDATE_INTERVAL)
                }

            }.onFailure { exception ->
                handleException(exception)
            }
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


}