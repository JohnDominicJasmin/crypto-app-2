package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

import com.mathroda.dashcoin.feature_coins.data.dto.FiatCurrencyItem
import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.domain.models.CoinCurrencyPreference
import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel
import com.mathroda.dashcoin.feature_coins.domain.models.GlobalMarketModel

data class CoinsState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val hasInternet: Boolean = true,
    val isItemsRendered: Boolean = false,
    val coinModels: List<CoinModel> = emptyList(),
    val globalMarket: GlobalMarketModel = GlobalMarketModel(),
    val tickerVisible:Boolean = false,

    val currencies: List<FiatCurrencyItem> = emptyList(),
    val errorMessage: String = "",
    val coinCurrencyPreference: CoinCurrencyPreference = CoinCurrencyPreference()


    )
