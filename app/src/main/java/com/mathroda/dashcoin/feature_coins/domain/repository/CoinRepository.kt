package com.mathroda.dashcoin.feature_coins.domain.repository

import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel
import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel
import com.mathroda.dashcoin.feature_coins.domain.models.NewsDetailModel

interface CoinRepository {

    suspend fun getCoins(): List<CoinModel>

    suspend fun getCoinById(coinId: String): CoinDetailModel

    suspend fun getChartsData(coinId: String, period: String): ChartModel

    suspend fun getNews(filter: String): List<NewsDetailModel>



}