package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

import com.mathroda.dashcoin.feature_coins.domain.models.CoinCurrencyPreference

sealed class CoinsEvent{
    data class RefreshCoins(val coinCurrencyPreference: CoinCurrencyPreference?): CoinsEvent()
    object CloseNoInternetDisplay: CoinsEvent()
    data class EnteredCoinsSearchQuery(val searchQuery: String): CoinsEvent()
    data class EnteredCurrencySearchQuery(val searchQuery: String): CoinsEvent()
    data class SelectCurrency(val coinCurrencyPreference: CoinCurrencyPreference): CoinsEvent()
}
