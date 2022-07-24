package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

import com.mathroda.dashcoin.feature_coins.domain.models.CoinCurrencyPreference

sealed class CoinsEvent{
    data class RefreshCoins(val currency: String?): CoinsEvent()
    object CloseNoInternetDisplay: CoinsEvent()
    data class EnteredSearchQuery(val searchQuery: String): CoinsEvent()
    data class SelectCurrency(val coinCurrencyPreference: CoinCurrencyPreference): CoinsEvent()
}
