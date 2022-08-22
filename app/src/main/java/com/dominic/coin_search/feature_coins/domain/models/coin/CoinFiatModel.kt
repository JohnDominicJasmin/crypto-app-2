package com.dominic.coin_search.feature_coins.domain.models.coin

import com.dominic.coin_search.feature_coins.data.dto.fiats.FiatCurrencyItem

data class CoinFiatModel(
    val currencies: List<FiatCurrencyItem> = emptyList()
)
