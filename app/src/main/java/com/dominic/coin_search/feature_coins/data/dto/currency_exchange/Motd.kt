package com.dominic.coin_search.feature_coins.data.dto.currency_exchange


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Motd(
    @SerializedName("msg")
    val msg: String,
    @SerializedName("url")
    val url: String
)