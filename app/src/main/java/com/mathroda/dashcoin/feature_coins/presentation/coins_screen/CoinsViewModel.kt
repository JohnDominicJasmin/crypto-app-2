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


    init {
        getCoins()
    }


    private fun getCoins() {
        viewModelScope.launch {
            runCatching {
                _state.value = state.copy(isLoading = true)
                coinUseCases.getCoins().collect { coins ->
                    _state.value = state.copy(isLoading = false, coinModels = coins)
                }
            }.onFailure { exception ->
                _state.value = state.copy(isLoading = false)

                when (exception) {
                    is CoinExceptions.UnexpectedErrorException -> {
                        _state.value = state.copy(errorMessage = exception.message!!)
                    }
                    is CoinExceptions.NoInternetException -> {
                        _state.value = state.copy(hasInternet = false)
                    }
                }
                this.cancel()
            }
        }
    }

    fun onEvent(event: CoinsEvent){
        when(event){
            is CoinsEvent.RefreshCoins -> {
                refresh()
            }

            is CoinsEvent.CloseNoInternetDisplay -> {
                _state.value = state.copy(hasInternet = true)
            }

            is CoinsEvent.EnteredSearchQuery -> {
                _state.value = state.copy(searchQuery = event.searchQuery)
            }
        }
    }
    private fun refresh() {
        viewModelScope.launch {
            _state.value = state.copy(isRefreshing = true)
            getCoins()
            _state.value = state.copy(isRefreshing = false)
        }

    }


}