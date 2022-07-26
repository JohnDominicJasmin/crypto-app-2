package com.mathroda.dashcoin.feature_coins.presentation.coin_detail


sealed class CoinDetailEvent{
    object CloseNoInternetDisplay: CoinDetailEvent()
    object ToggleFavoriteCoin: CoinDetailEvent()
    object LoadCoinDetail: CoinDetailEvent()
}
