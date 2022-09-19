package com.dominic.coin_search.feature_coins.domain.models.coin

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.dominic.coin_search.feature_coins.data.dto.fiats.FiatCurrencyItem

@Stable
@Immutable
data class CoinFiatModel(
    val currencies: List<FiatCurrencyItem> = emptyList()
)
