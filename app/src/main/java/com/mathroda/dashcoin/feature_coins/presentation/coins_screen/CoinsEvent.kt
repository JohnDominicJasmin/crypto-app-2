package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

sealed class CoinsEvent{
    object RefreshCoins: CoinsEvent()
    data class EnteredSearchQuery(val searchQuery: String): CoinsEvent()
}
