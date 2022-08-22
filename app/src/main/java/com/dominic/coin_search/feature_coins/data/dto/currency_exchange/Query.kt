package com.dominic.coin_search.feature_coins.data.dto.currency_exchange


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Query(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("from")
    val from: String,
    @SerializedName("to")
    val to: String
)