package com.mathroda.dashcoin.feature_coins.domain.use_case

import com.mathroda.dashcoin.feature_coins.domain.models.GlobalMarketModel
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_chart.GetChartUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_coin.GetCoinUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_coins.GetCoinsUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_market_status.GetGlobalMarketUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_news.GetNewsUseCase

data class CoinUseCases (

    val getCoins: GetCoinsUseCase,
    val getCoin: GetCoinUseCase,
    val getChart: GetChartUseCase,
    val getNews: GetNewsUseCase,
    val getGlobalMarket: GetGlobalMarketUseCase

)