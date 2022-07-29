package com.mathroda.dashcoin.feature_coins.presentation.coin_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathroda.dashcoin.core.util.Constants
import com.mathroda.dashcoin.core.util.Constants.UPDATE_INTERVAL
import com.mathroda.dashcoin.feature_coins.domain.exceptions.CoinExceptions
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
): ViewModel() {


    private val _state = MutableStateFlow(CoinDetailState())
    val state = _state.asStateFlow()

    init {
        loadCoinDetail()
    }

    private fun loadCoinDetail(){
            savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
                getCoin(coinId)
                getChart(coinId)
                isFavoriteCoin()
            }
    }


    fun onEvent(event: CoinDetailEvent){
        when(event){
            is CoinDetailEvent.CloseNoInternetDisplay -> {
                _state.update { it.copy(hasInternet = true) }
            }
            is CoinDetailEvent.ToggleFavoriteCoin -> {
                _state.update { it.copy(isFavorite = !_state.value.isFavorite) }
            }
            is CoinDetailEvent.LoadCoinDetail -> {
                loadCoinDetail()

            }
        }

    }


    private fun isFavoriteCoin() {

        viewModelScope.launch {
            favoriteUseCase.getAllCoins().distinctUntilChanged().cancellable().onEach { coins ->
                coins.any { it.name == _state.value.coinDetailModel?.name  }
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
                    _state.update{it.copy(isLoading = true)}
                    while (isActive) {
                        coinUseCase.getCoin(coinId).distinctUntilChanged().collect { coinDetail ->
                            _state.update { it.copy(isLoading = false, coinDetailModel = coinDetail) }
                        }
                        delay(UPDATE_INTERVAL)
                    }

                }.onFailure { exception ->
                    handleException(exception)
                    this.cancel()
                }
            }
     }
    private fun handleException(exception: Throwable){
        _state.update { it.copy(isLoading = false) }
        when (exception) {
            is CoinExceptions.UnexpectedErrorException -> {
                _state.update { it.copy(errorMessage = exception.message!!) }
            }
            is CoinExceptions.NoInternetException -> {
                _state.update { it.copy(hasInternet = false) }
            }
        }
    }
    private fun getChart(coinId: String) {

        viewModelScope.launch {
            runCatching {
                _state.update { it.copy(isLoading = true) }
                    while(isActive) {
                        coinUseCase.getChart(coinId, period = "24h").collect{ chartModel ->
                            _state.update { it.copy(isLoading = false, chartModel = chartModel) }
                        }
                        delay(UPDATE_INTERVAL)
                    }
            }.onFailure { exception ->
                handleException(exception)
                this.cancel()
            }
        }
    }






}