package com.dominic.coin_search.feature_coins.presentation.coins_screen

import com.dominic.coin_search.feature_coins.data.dto.fiats.FiatCurrencyItem
import com.dominic.coin_search.feature_coins.domain.models.coin.CoinCurrencyPreference
import com.dominic.coin_search.feature_coins.domain.models.coin.CoinFiatModel
import com.dominic.coin_search.feature_coins.domain.models.coin.CoinModel
import com.dominic.coin_search.feature_coins.domain.models.coin.CoinGlobalMarketModel

data class CoinsState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val hasInternet: Boolean = true,
    val isItemsRendered: Boolean = false,
    val coinModels: List<CoinModel> = emptyList(),
    val globalMarket: CoinGlobalMarketModel = CoinGlobalMarketModel(),
    val tickerVisible:Boolean = false,
    val currencies: CoinFiatModel = CoinFiatModel(),
    val errorMessage: String = "",
    val coinCurrencyPreference: CoinCurrencyPreference = CoinCurrencyPreference()


    )
