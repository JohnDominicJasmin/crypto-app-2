package com.dominic.coin_search.feature_coins.presentation.coins_screen

import com.dominic.coin_search.feature_coins.domain.models.coin.CoinCurrencyPreference

sealed class CoinsEvent{
    data class RefreshInformation(val coinCurrencyPreference: CoinCurrencyPreference): CoinsEvent()
    data class RefreshCoins(val coinCurrencyPreference: CoinCurrencyPreference): CoinsEvent()
    object CloseNoInternetDisplay: CoinsEvent()
    data class SelectCurrency(val coinCurrencyPreference: CoinCurrencyPreference): CoinsEvent()
}
