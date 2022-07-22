package com.mathroda.dashcoin.feature_coins.data.repository

import com.mathroda.dashcoin.feature_coins.data.remote.CoinStatsApi
import com.mathroda.dashcoin.feature_coins.data.mapper.CoinMapper.toChart
import com.mathroda.dashcoin.feature_coins.data.mapper.CoinMapper.toCoinDetail
import com.mathroda.dashcoin.feature_coins.data.mapper.CoinMapper.toCoinFiat
import com.mathroda.dashcoin.feature_coins.data.mapper.CoinMapper.toCoins
import com.mathroda.dashcoin.feature_coins.data.mapper.CoinMapper.toGlobalMarket
import com.mathroda.dashcoin.feature_coins.data.mapper.CoinMapper.toNewsDetail
import com.mathroda.dashcoin.feature_coins.data.remote.CoinPaprikaApi
import com.mathroda.dashcoin.feature_coins.domain.exceptions.CoinExceptions
import com.mathroda.dashcoin.feature_coins.domain.models.*
import com.mathroda.dashcoin.feature_coins.domain.repository.CoinRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinStatsApi: CoinStatsApi,
    private val coinPaprikaAPi: CoinPaprikaApi,
) : CoinRepository {


    override suspend fun getFiats(): CoinFiatModel =
        handleException {
            coinStatsApi.getFiats().toCoinFiat()
        }

    override suspend fun getGlobalMarket(): GlobalMarketModel =
        handleException {
            coinPaprikaAPi.getGlobalMarket().toGlobalMarket()
        }


    override suspend fun getCoins(): List<CoinModel> =
        handleException {
            coinStatsApi.getCoins().coins.map { it.toCoins() }
        }


    override suspend fun getCoinById(coinId: String): CoinDetailModel =
        handleException {
            coinStatsApi.getCoinById(coinId).coin.toCoinDetail()
        }

    override suspend fun getChartsData(coinId: String, period: String): ChartModel =
        handleException {
            coinStatsApi.getChartsData(coinId, period).toChart()
        }


    override suspend fun getNews(filter: String): List<NewsDetailModel> =
        handleException {
            coinStatsApi.getNews(filter).news.map { it.toNewsDetail() }
        }

}


private suspend fun <T> handleException(action: suspend () -> T): T {
    return try {
        action()
    } catch (e: HttpException) {
        throw CoinExceptions.UnexpectedErrorException()
    } catch (e: IOException) {
        throw CoinExceptions.NoInternetException()
    }
}