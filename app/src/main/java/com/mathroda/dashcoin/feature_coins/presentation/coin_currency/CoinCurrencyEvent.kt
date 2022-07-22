package com.mathroda.dashcoin.feature_coins.presentation.coin_currency

sealed class CoinCurrencyEvent{
    object SelectCurrency: CoinCurrencyEvent()
    object CloseScreen: CoinCurrencyEvent()

}
