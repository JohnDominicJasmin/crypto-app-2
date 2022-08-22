package com.dominic.coin_search.feature_coins.data.dto.coin_information


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Links(
    @SerializedName("explorer")
    val explorer: List<String>,
    @SerializedName("facebook")
    val facebook: List<String>,
    @SerializedName("medium")
    val medium: Any,
    @SerializedName("reddit")
    val reddit: List<String>,
    @SerializedName("source_code")
    val sourceCode: List<String>,
    @SerializedName("website")
    val website: List<String>,
    @SerializedName("youtube")
    val youtube: List<String>
)