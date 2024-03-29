package com.dominic.coin_search.feature_coins.domain.repository

import com.dominic.coin_search.feature_coins.domain.models.chart.ChartModel
import com.dominic.coin_search.feature_coins.domain.models.coin.*
import com.dominic.coin_search.feature_coins.domain.models.currency.CurrencyExchangeModel
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    suspend fun getCoins(currency: String): List<CoinModel>

    suspend fun getCoinById(coinId: String, currency: String): CoinDetailModel

    suspend fun getChartsData(coinId: String, period: String): ChartModel

    suspend fun getNews(filter: String): List<NewsModel>

    suspend fun getGlobalMarket(): CoinGlobalMarketModel

    suspend fun getFiats(): CoinFiatModel

    suspend fun updateCurrency(coinCurrencyPreference: CoinCurrencyPreference)

    suspend fun getCurrency(): Flow<CoinCurrencyPreference>

    suspend fun updateChartPeriod(period: String)

    suspend fun getChartPeriod(): Flow<String?>

    suspend fun getCoinInformation(coinId: String): CoinInformationModel

    suspend fun getCurrencyExchangeRate(currency: String): CurrencyExchangeModel
}