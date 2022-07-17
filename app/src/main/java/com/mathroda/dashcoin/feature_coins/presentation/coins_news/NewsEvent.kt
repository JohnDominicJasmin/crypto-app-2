package com.mathroda.dashcoin.feature_coins.presentation.coins_news

sealed class NewsEvent{
    object RefreshNews: NewsEvent()
    data class EnteredSearchQuery(val searchQuery: String): NewsEvent()
}
