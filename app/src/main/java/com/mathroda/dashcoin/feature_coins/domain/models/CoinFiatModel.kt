package com.mathroda.dashcoin.feature_coins.domain.models

import com.mathroda.dashcoin.feature_coins.data.dto.FiatCurrencyItem

data class CoinFiatModel(
    val currencies: List<FiatCurrencyItem>
)
