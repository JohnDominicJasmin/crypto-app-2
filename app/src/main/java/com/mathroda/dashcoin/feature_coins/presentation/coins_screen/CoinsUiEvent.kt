package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

sealed class CoinsUiEvent{
    data class ShowToastMessage(val message: String): CoinsUiEvent()
}
