package com.mathroda.dashcoin.feature_coins.domain.repository

import com.mathroda.dashcoin.feature_coins.domain.models.*

interface CoinRepository {

    suspend fun getCoins(): List<CoinModel>

    suspend fun getCoinById(coinId: String): CoinDetailModel

    suspend fun getChartsData(coinId: String, period: String): ChartModel

    suspend fun getNews(filter: String): List<NewsDetailModel>

    suspend fun getGlobalMarket():GlobalMarketModel

    suspend fun getFiats():CoinFiatModel

}