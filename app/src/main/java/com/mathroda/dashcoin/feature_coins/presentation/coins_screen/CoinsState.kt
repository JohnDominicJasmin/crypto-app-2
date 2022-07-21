package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

import androidx.compose.ui.text.input.TextFieldValue
import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel
import com.mathroda.dashcoin.feature_coins.domain.models.GlobalMarketModel

data class CoinsState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val hasInternet: Boolean = true,
    val isItemsRendered: Boolean = false,
    val coin: List<CoinModel> = emptyList(),
    val chart: MutableList<ChartModel> = mutableListOf(),
    val globalMarket: GlobalMarketModel = GlobalMarketModel(),
    val errorMessage: String = "",
    val searchQuery: String = "",
    val tickerVisible:Boolean = false,


    )
