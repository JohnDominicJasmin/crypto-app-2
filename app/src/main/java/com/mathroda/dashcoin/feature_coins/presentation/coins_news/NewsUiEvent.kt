package com.mathroda.dashcoin.feature_coins.presentation.coins_news

import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.CoinDetailUiEvent

sealed class NewsUiEvent{
    object ShowNoInternetScreen: NewsUiEvent()
    data class ShowToastMessage(val message: String): NewsUiEvent()
}
