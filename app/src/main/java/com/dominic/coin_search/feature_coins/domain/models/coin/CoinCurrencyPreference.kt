package com.dominic.coin_search.feature_coins.domain.models.coin

import androidx.compose.runtime.Stable

@Stable
data class CoinCurrencyPreference(
    val currency: String? = null,
    val currencySymbol: String? =null
)
