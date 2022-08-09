package com.dominic.coin_search.feature_coins.data.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Parent(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String
)