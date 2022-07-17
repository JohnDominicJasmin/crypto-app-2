package com.mathroda.dashcoin.feature_coins.data.repository

import com.mathroda.dashcoin.feature_coins.data.data_source.remote.DashCoinApi
import com.mathroda.dashcoin.feature_coins.data.mapper.CoinMapper.toChart
import com.mathroda.dashcoin.feature_coins.data.mapper.CoinMapper.toCoinDetail
import com.mathroda.dashcoin.feature_coins.data.mapper.CoinMapper.toCoins
import com.mathroda.dashcoin.feature_coins.data.mapper.CoinMapper.toNewsDetail
import com.mathroda.dashcoin.feature_coins.domain.exceptions.CoinExceptions
import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel
import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel
import com.mathroda.dashcoin.feature_coins.domain.models.NewsDetailModel
import com.mathroda.dashcoin.feature_coins.domain.repository.CoinRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: DashCoinApi,
): CoinRepository {


    override suspend fun getCoins(): List<CoinModel> {
        return try{
            api.getCoins().coins.map { it.toCoins() }
        }catch (e: HttpException){
            throw CoinExceptions.UnexpectedErrorException()
        }catch (e: IOException){
            throw CoinExceptions.NoInternetException()
        }

    }

    override suspend fun getCoinById(coinId: String): CoinDetailModel {
        return try{
            api.getCoinById(coinId).coin.toCoinDetail()
        }catch(e: HttpException){
            throw CoinExceptions.UnexpectedErrorException()
        }catch (e: IOException){
            throw CoinExceptions.NoInternetException()
        }
    }

    override suspend fun getChartsData(coinId: String, period: String): ChartModel {

        return try{
            api.getChartsData(coinId, period).toChart()
        }catch (e: HttpException){
            throw CoinExceptions.UnexpectedErrorException()
        }catch (e: IOException){
            throw CoinExceptions.NoInternetException()
        }
    }

    override suspend fun getNews(filter: String): List<NewsDetailModel> {
        return try{
            api.getNews(filter).news.map { it.toNewsDetail()}
        }catch(e: HttpException){
            throw CoinExceptions.UnexpectedErrorException()
        }catch(e: IOException){
            throw CoinExceptions.NoInternetException()
        }
    }



}