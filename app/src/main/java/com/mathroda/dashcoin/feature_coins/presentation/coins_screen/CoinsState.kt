package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

import androidx.compose.ui.text.input.TextFieldValue
import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel

data class CoinsState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val hasInternet: Boolean = true,
    val coinModels: List<CoinModel> = emptyList(),
    val errorMessage: String = "",
    val searchQuery: String = ""
)
