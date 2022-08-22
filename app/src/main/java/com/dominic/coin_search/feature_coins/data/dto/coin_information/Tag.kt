package com.dominic.coin_search.feature_coins.data.dto.coin_information


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Tag(
    @SerializedName("coin_counter")
    val coinCounter: Int,
    @SerializedName("ico_counter")
    val icoCounter: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)