package com.dominic.coin_search.feature_coins.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.dominic.coin_search.core.util.Constants.CHART_PERIOD
import com.dominic.coin_search.core.util.Constants.CURRENCY_SYMBOL
import com.dominic.coin_search.core.util.Constants.CURRENCY
import com.dominic.coin_search.core.util.extension.dataStore
import com.dominic.coin_search.feature_coins.data.remote.CoinStatsApi
import com.dominic.coin_search.feature_coins.data.mapper.CoinMapper.toChart
import com.dominic.coin_search.feature_coins.data.mapper.CoinMapper.toCoinDetail
import com.dominic.coin_search.feature_coins.data.mapper.CoinMapper.toCoinFiat
import com.dominic.coin_search.feature_coins.data.mapper.CoinMapper.toCoinInformation
import com.dominic.coin_search.feature_coins.data.mapper.CoinMapper.toCoins
import com.dominic.coin_search.feature_coins.data.mapper.CoinMapper.toCurrencyExchange
import com.dominic.coin_search.feature_coins.data.mapper.CoinMapper.toGlobalMarket
import com.dominic.coin_search.feature_coins.data.mapper.CoinMapper.toNewsDetail
import com.dominic.coin_search.feature_coins.data.remote.CoinPaprikaApi
import com.dominic.coin_search.feature_coins.data.remote.ExchangeRateApi
import com.dominic.coin_search.feature_coins.domain.exceptions.CoinExceptions
import com.dominic.coin_search.feature_coins.domain.models.chart.ChartModel
import com.dominic.coin_search.feature_coins.domain.models.coin.*
import com.dominic.coin_search.feature_coins.domain.models.currency.CurrencyExchangeModel
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import com.dominic.coin_search.feature_coins.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject


class CoinRepositoryImpl @Inject constructor(
    private val coinStatsApi: CoinStatsApi,
    private val coinPaprikaAPi: CoinPaprikaApi,
    private val exchangeRateApi: ExchangeRateApi,
    val context:Context,
) : CoinRepository {

    private var dataStore = context.dataStore


    override suspend fun updateChartPeriod(period: String) {
        dataStore.edit{preferences ->
            preferences[CHART_PERIOD] = period
        }
    }

    override suspend fun getChartPeriod(): Flow<String?> {
        return dataStore.data.catch{ exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            }else{
                Timber.e(message = exception.localizedMessage ?: "Unexpected error occurred.")
            }
        }.map{ preference ->
            preference[CHART_PERIOD]
        }
    }

    override suspend fun updateCurrency(coinCurrencyPreference: CoinCurrencyPreference) {
        dataStore.edit{preferences ->
            preferences[CURRENCY] = coinCurrencyPreference.currency ?: "USD"
            preferences[CURRENCY_SYMBOL] = coinCurrencyPreference.currencySymbol ?: "$"
        }
    }

    override suspend fun getCurrency(): Flow<CoinCurrencyPreference> {
        return dataStore.data.catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            }else{
                Timber.e(message = exception.localizedMessage ?: "Unexpected error occurred.")
            }
        }.map { preference ->
            val currency = preference[CURRENCY]
            val currencySymbol = preference[CURRENCY_SYMBOL]
            CoinCurrencyPreference(currency = currency ?: "USD", currencySymbol = currencySymbol ?: "$")
        }
    }





    override suspend fun getFiats(): CoinFiatModel =
        handleException {
            coinStatsApi.getFiats().toCoinFiat()
        }

    override suspend fun getGlobalMarket(): CoinGlobalMarketModel =
        handleException {
            coinPaprikaAPi.getGlobalMarket().toGlobalMarket()
        }


    override suspend fun getCoins(currency:String): List<CoinModel> =
        handleException {
            coinStatsApi.getCoins(currency).coins.map { it.toCoins() }
        }


    override suspend fun getCoinById(coinId: String, currency: String): CoinDetailModel =
        handleException {
            coinStatsApi.getCoinById(coinId, currency).coin.toCoinDetail()
        }

    override suspend fun getChartsData(coinId: String, period: String): ChartModel =
        handleException {
            coinStatsApi.getChartsData(coinId, period).toChart()
        }


    override suspend fun getNews(filter: String): List<NewsModel> =
        handleException {
            coinStatsApi.getNews(filter).news.map { it.toNewsDetail() }
        }


    override suspend fun getCoinInformation(coinId: String): CoinInformationModel {
          return  coinPaprikaAPi.getCoinInformation(coinId).toCoinInformation()
    }

    override suspend fun getCurrencyExchangeRate(currency: String): CurrencyExchangeModel {
        return exchangeRateApi.getExchangeRate(to = currency).toCurrencyExchange()
    }
}


private inline fun <T> handleException(action: () -> T): T {
    return try {
        action()
    } catch (e: HttpException) {
        throw CoinExceptions.UnexpectedErrorException()
    } catch (e: IOException) {
        throw CoinExceptions.NoInternetException()
    }
}