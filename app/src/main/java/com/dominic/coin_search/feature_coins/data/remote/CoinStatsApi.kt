package com.dominic.coin_search.feature_coins.data.remote

import com.dominic.coin_search.core.util.Constants.COINS_LIMIT
import com.dominic.coin_search.feature_coins.data.dto.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinStatsApi {

    @GET("v1/coins")
    suspend fun getCoins(
        @Query("currency") currency: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = COINS_LIMIT
    ): CoinsDto

    @GET("v1/coins/{coinId}")
    suspend fun getCoinById(
        @Path("coinId") coinId: String,
        @Query("currency") currency: String
    ): CoinDetailDto

    @GET("v1/charts")
    suspend fun getChartsData(
        @Query("coinId") coinId: String,
        @Query("period") period: String,
    ): ChartDto


    @GET("v1/news/{filter}")
    suspend fun getNews(
        @Path("filter") filter: String,
        /**
         * available filters
         * 1. handpicked
         * 2. trending
         * 3. latest
         * 4. bullish
         * 5. bearish
         */
        @Query("limit") limit: Int = 50,
        @Query("skip") skip: Int = 0
    ): NewsDto


    @GET("v1/fiats")
    suspend fun getFiats():FiatCurrencyDto

}