package com.dominic.coin_search.feature_coins.presentation.coins_news

sealed class NewsEvent{
    object RefreshNews: NewsEvent()
    object CloseNoInternetDisplay: NewsEvent()
}
