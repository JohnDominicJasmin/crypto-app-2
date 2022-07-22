package com.mathroda.dashcoin.feature_coins.presentation.coin_currency

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathroda.dashcoin.feature_coins.domain.use_case.CoinUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinCurrencyViewModel @Inject constructor(
    private val coinUseCase: CoinUseCases) : ViewModel() {

    private val _state = mutableStateOf(CoinCurrencyState())
    val state by _state

    init {
        getCoinCurrencies()
    }

    private fun getCoinCurrencies(){
        viewModelScope.launch {
            runCatching {
                coinUseCase.getFiats().currencies
            }.onSuccess { currencies ->
                _state.value = state.copy(currencies = currencies)
            }.onFailure {
                _state.value = state.copy(errorMessage = it.message!!)
            }
        }
    }
}