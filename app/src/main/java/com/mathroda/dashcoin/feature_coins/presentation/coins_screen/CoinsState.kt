package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

import androidx.compose.ui.text.input.TextFieldValue
import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel

data class CoinsState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val coinModels: List<CoinModel> = emptyList(),
    val chartModels: List<ChartModel> = emptyList(),
    val errorMessage: String = "",
    val searchQuery: String = ""
)
