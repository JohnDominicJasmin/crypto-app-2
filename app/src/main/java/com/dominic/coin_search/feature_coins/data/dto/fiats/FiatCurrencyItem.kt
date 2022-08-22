package com.dominic.coin_search.feature_coins.data.dto.fiats


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class FiatCurrencyItem(
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("symbol")
    val symbol: String
)