package com.mathroda.dashcoin.feature_coins.presentation.coin_detail

sealed class CoinDetailUiEvent{
    object ShowNoInternetScreen: CoinDetailUiEvent()
    data class ShowToastMessage(val message: String): CoinDetailUiEvent()
}
