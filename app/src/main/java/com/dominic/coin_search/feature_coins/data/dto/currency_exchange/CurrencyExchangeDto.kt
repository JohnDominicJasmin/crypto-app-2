package com.dominic.coin_search.feature_coins.data.dto.currency_exchange


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CurrencyExchangeDto(
    @SerializedName("date")
    val date: String,
    @SerializedName("historical")
    val historical: Boolean,
    @SerializedName("info")
    val info: Info,
    @SerializedName("motd")
    val motd: Motd,
    @SerializedName("query")
    val query: Query,
    @SerializedName("result")
    val result: Double,
    @SerializedName("success")
    val success: Boolean
)