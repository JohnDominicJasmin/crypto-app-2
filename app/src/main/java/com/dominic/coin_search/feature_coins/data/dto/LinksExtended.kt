package com.dominic.coin_search.feature_coins.data.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LinksExtended(
    @SerializedName("stats")
    val stats: Stats,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)