package com.dominic.coin_search.feature_coins.data.dto.coin_information


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Team(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("position")
    val position: String
)