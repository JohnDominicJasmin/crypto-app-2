package com.mathroda.dashcoin.feature_coins.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.mathroda.dashcoin.core.util.Constants.CURRENCY_SYMBOL
import com.mathroda.dashcoin.core.util.Constants.CURRENCY
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "preferences")

class CoinRepositoryImpl @Inject constructor(
    private val coinStatsApi: CoinStatsApi,
    private val coinPaprikaAPi: CoinPaprikaApi,
    val context:Context,
) : CoinRepository {

    private var dataStore = context.dataStore

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

    override suspend fun getGlobalMarket(): GlobalMarketModel =
        handleException {
            coinPaprikaAPi.getGlobalMarket().toGlobalMarket()
        }


    override suspend fun getCoins(currency:String): List<CoinModel> =
        handleException {
            Timber.v("CURRENCY USED IS $currency")
            coinStatsApi.getCoins(currency).coins.map { it.toCoins() }
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