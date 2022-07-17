package com.mathroda.dashcoin.feature_coins.presentation.coin_detail.utils


sealed class CoinDetailEvent{
    object ToggleFavoriteCoin: CoinDetailEvent()
}
