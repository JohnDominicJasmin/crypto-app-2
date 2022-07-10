package com.mathroda.dashcoin.feature_coins.presentation.coin_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathroda.dashcoin.core.util.Constants
import com.mathroda.dashcoin.feature_coins.domain.exceptions.CoinExceptions
import com.mathroda.dashcoin.feature_coins.domain.use_case.CoinUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinUseCase: CoinUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {


    private val _state = mutableStateOf(CoinDetailState())
    val state by _state

    private val _eventFlow = MutableSharedFlow<CoinDetailUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {

        viewModelScope.launch {
            savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
                getChart(coinId)
                getCoin(coinId)
            }
            getMarketStatus()

        }

    }


     private suspend fun getCoin(coinId: String) {
         coroutineScope {
             runCatching {
                 _state.value = state.copy(isLoading = true)
                 coinUseCase.getCoin(coinId)

             }.onSuccess { coinDetailFlow ->
                 coinDetailFlow.onEach { coinDetail ->
                     _state.value = state.copy(isLoading = false, coinDetailModel = coinDetail)
                 }.launchIn(this)

             }.onFailure { exception ->
                 handleException(exception)
             }
         }
     }
    private suspend fun handleException(exception: Throwable){
        _state.value = state.copy(isLoading = false)

        when (exception) {
            is CoinExceptions.UnexpectedErrorException -> {
                _eventFlow.emit(value = CoinDetailUiEvent.ShowToastMessage(exception.message!!))
            }
            is CoinExceptions.NoInternetException -> {
                _eventFlow.emit(value = CoinDetailUiEvent.ShowNoInternetScreen)
            }
        }
    }
    private suspend fun getChart(coinId: String) {

        coroutineScope {
            runCatching {
                coinUseCase.getChart(coinId)
            }.onSuccess { chartFlow ->
                chartFlow.onEach { chart ->
                    _state.value = state.copy(isLoading = false, chartModel = chart)
                }.launchIn(this)
            }.onFailure { exception ->
                handleException(exception)
            }
        }
    }



    private suspend fun getMarketStatus(coinId: String = "bitcoin") {

        coroutineScope {
            runCatching {
                _state.value = state.copy(isLoading = true)
                coinUseCase.getCoin(coinId)
            }.onSuccess { coinDetailFlow ->
                coinDetailFlow.onEach { coinDetail ->
                    _state.value = state.copy(isLoading = false, coinDetailModel = coinDetail)
                }.launchIn(this)
            }.onFailure { exception ->
                handleException(exception)
            }
        }
    }
}