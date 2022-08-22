package com.dominic.coin_search.feature_coins.data.dto.coin_information


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Contract(
    @SerializedName("contract")
    val contract: String,
    @SerializedName("platform")
    val platform: String,
    @SerializedName("type")
    val type: String
)