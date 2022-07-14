package com.mathroda.dashcoin.feature_coins.data.data_source.remote

import com.mathroda.dashcoin.feature_coins.data.dto.ChartDto
import com.mathroda.dashcoin.feature_coins.data.dto.CoinDetailDto
import com.mathroda.dashcoin.feature_coins.data.dto.CoinsDto
import com.mathroda.dashcoin.feature_coins.data.dto.NewsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DashCoinApi {

    @GET("v1/coins")
    suspend fun getCoins(
        @Query("currency") currency: String = "USD",
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 200
    ): CoinsDto

    @GET("v1/coins/{coinId}")
    suspend fun getCoinById(
        @Path("coinId") coinId: String
    ): CoinDetailDto

    @GET("v1/charts")
    suspend fun getChartsData(
        @Query("coinId") coinId: String,
        @Query("period") period: String = "24h" //available periods - 24h | 1w | 1m | 3m | 6m | 1y | all
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
}