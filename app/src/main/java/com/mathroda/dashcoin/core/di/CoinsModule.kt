package com.mathroda.dashcoin.core.di

import android.content.Context
import androidx.annotation.Keep
import com.mathroda.dashcoin.core.util.ConnectionStatus
import com.mathroda.dashcoin.core.util.Constants
import com.mathroda.dashcoin.core.util.Constants.HEADER_CACHE_CONTROL
import com.mathroda.dashcoin.core.util.Constants.HEADER_PRAGMA
import com.mathroda.dashcoin.feature_coins.data.remote.CoinPaprikaApi
import com.mathroda.dashcoin.feature_coins.data.remote.CoinStatsApi
import com.mathroda.dashcoin.feature_coins.data.repository.CoinRepositoryImpl
import com.mathroda.dashcoin.feature_coins.domain.repository.CoinRepository
import com.mathroda.dashcoin.feature_coins.domain.use_case.CoinUseCases
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_chart.GetChartUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_coin.GetCoinUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_coins.GetCoinsUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_currency.GetCurrencyUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_fiats.GetFiatsUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_market_status.GetGlobalMarketUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_news.GetNewsUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.update_currency.UpdateCurrencyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Keep
@Module
@InstallIn(SingletonComponent::class)
object CoinsModule {

    @Provides
    @Singleton
    fun providesCoinStatsApi(okHttpClient: OkHttpClient): CoinStatsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.COIN_STATS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CoinStatsApi::class.java)
    }


    @Provides
    @Singleton
    fun providesCoinPaprikaApi(okHttpClient: OkHttpClient): CoinPaprikaApi {
        return Retrofit.Builder()
            .baseUrl(Constants.COIN_PAPRIKA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CoinPaprikaApi::class.java)
    }


    @Provides
    @Singleton
    fun providesCoinRepository(
        @ApplicationContext context: Context,
        coinStatsApi: CoinStatsApi,
        coinPaprikaAPi: CoinPaprikaApi): CoinRepository {
        return CoinRepositoryImpl(coinStatsApi, coinPaprikaAPi,context)
    }


    @Provides
    @Singleton
    fun providesCoinUseCase(repository: CoinRepository): CoinUseCases {
        return CoinUseCases(
            getCoins = GetCoinsUseCase(repository),
            getCoin = GetCoinUseCase(repository),
            getChart = GetChartUseCase(repository),
            getNews = GetNewsUseCase(repository),
            getGlobalMarket = GetGlobalMarketUseCase(repository),
            getFiats = GetFiatsUseCase(repository),
            getCurrency = GetCurrencyUseCase(repository),
            updateCurrency = UpdateCurrencyUseCase(repository)

            )
    }


    @Provides
    @Singleton
    @Named("NetworkInterceptor")
    fun providesNetworkInterceptor(): Interceptor {
        return Interceptor { chain ->

            val response = chain.proceed(chain.request())
            val cacheControl = CacheControl.Builder()
                .maxAge(40, TimeUnit.SECONDS)
                .build()

            response.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()

        }
    }


    @Provides
    @Singleton
    @Named("OfflineInterceptor")
    fun providesOfflineInterceptor(@ApplicationContext context: Context): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!ConnectionStatus.hasInternetConnection(context)) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(900, TimeUnit.DAYS)
                    .build()

                request = request.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .cacheControl(cacheControl)
                    .build()
            }
            chain.proceed(request)
        }
    }


    @Provides
    @Singleton
    @Named("LoggingInterceptor")
    fun providesLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor {
            Timber.d("LOGGING INTERCEPTOR: $it")
        }.also {
            it.setLevel(HttpLoggingInterceptor.Level.BODY)
        }

    }


    @Provides
    @Singleton
    fun providesCache(@ApplicationContext context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir, "offlineCache")
        val cacheSize = 50 * 1024 * 1024
        return Cache(httpCacheDirectory, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        cache: Cache,
        @Named("OfflineInterceptor") offlineInterceptor: Interceptor,
        @Named("NetworkInterceptor") networkInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(networkInterceptor)
            .addInterceptor(offlineInterceptor)
            .build()
    }
}