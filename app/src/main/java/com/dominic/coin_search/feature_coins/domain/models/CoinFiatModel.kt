package com.dominic.coin_search.feature_coins.domain.models

import com.dominic.coin_search.feature_coins.data.dto.FiatCurrencyItem

data class CoinFiatModel(
    val currencies: List<FiatCurrencyItem>
)
