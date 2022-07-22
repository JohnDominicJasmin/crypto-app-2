package com.mathroda.dashcoin.feature_coins.presentation.coin_currency

import com.mathroda.dashcoin.feature_coins.data.dto.FiatCurrencyItem

data class CoinCurrencyState(
    val currencies: List<FiatCurrencyItem> = emptyList(),
    val errorMessage: String = ""
)
