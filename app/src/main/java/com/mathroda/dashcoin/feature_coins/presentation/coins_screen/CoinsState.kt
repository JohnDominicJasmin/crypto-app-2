package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel

data class CoinsState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val coinModels: List<CoinModel> = emptyList(),
)
