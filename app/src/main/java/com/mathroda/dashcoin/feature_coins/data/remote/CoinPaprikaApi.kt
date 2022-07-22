package com.mathroda.dashcoin.feature_coins.data.remote

import com.mathroda.dashcoin.feature_coins.data.dto.GlobalMarketDto
import retrofit2.http.GET

interface CoinPaprikaApi {

    @GET("/v1/global")
    suspend fun getGlobalMarket(): GlobalMarketDto

}