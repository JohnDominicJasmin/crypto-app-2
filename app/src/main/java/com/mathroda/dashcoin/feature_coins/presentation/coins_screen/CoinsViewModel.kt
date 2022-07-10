package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathroda.dashcoin.feature_coins.domain.exceptions.CoinExceptions
import com.mathroda.dashcoin.feature_coins.domain.use_case.CoinUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val coinUseCases: CoinUseCases
): ViewModel() {

    private val _state = mutableStateOf(CoinsState())
    val state by _state


    private val _eventFlow = MutableSharedFlow<CoinsUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()



    init {
        getCoins()
    }


    private fun getCoins() {
        viewModelScope.launch {
            runCatching {
                _state.value = state.copy(isLoading = true)
                coinUseCases.getCoins()
            }.onSuccess { coinsFlow ->
                coinsFlow.onEach { coins ->
                    _state.value = state.copy(isLoading = false, coinModels = coins)
                    }.launchIn(this)
            }.onFailure { exception ->
                _state.value = state.copy(isLoading = false)

                when (exception) {
                    is CoinExceptions.UnexpectedErrorException -> {
                        _eventFlow.emit(value = CoinsUiEvent.ShowToastMessage(exception.message!!))
                    }
                    is CoinExceptions.NoInternetException -> {
                        _eventFlow.emit(value = CoinsUiEvent.ShowNoInternetScreen)
                    }
                }
                this.cancel()
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _state.value = state.copy(isRefreshing = true)
            getCoins()
            _state.value = state.copy(isRefreshing = false)
        }

    }


}