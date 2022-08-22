package com.dominic.coin_search.feature_coins.data.remote

import com.dominic.coin_search.feature_coins.data.dto.currency_exchange.CurrencyExchangeDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {

    @GET("/convert")
    suspend fun getExchangeRate(
        @Query("from") from: String = "USD",
        @Query("to") to: String,
    ): CurrencyExchangeDto
}