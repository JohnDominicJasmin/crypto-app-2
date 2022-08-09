package com.dominic.coin_search.feature_coins.data.remote

import com.dominic.coin_search.feature_coins.data.dto.CoinInformationDto
import com.dominic.coin_search.feature_coins.data.dto.GlobalMarketDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {

    @GET("/v1/global")
    suspend fun getGlobalMarket(): GlobalMarketDto

    @GET("/v1/coins/{coinId}")
    suspend fun getCoinInformation(@Path("coinId") coinId:String): CoinInformationDto

}