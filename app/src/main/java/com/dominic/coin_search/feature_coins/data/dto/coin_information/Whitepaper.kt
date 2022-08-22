package com.dominic.coin_search.feature_coins.data.dto.coin_information


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Whitepaper(
    @SerializedName("link")
    val link: String,
    @SerializedName("thumbnail")
    val thumbnail: String
)