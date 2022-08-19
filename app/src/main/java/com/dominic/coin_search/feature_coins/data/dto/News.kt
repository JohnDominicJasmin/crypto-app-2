package com.dominic.coin_search.feature_coins.data.dto

data class News(
    val coins: List<Any>,
    val content: Boolean,
    val description: String,
    val feedDate: Long,
    val icon: String,
    val id: String,
    val imgURL: String,
    val link: String,
    val reactionsCount: ReactionsCount,
    val relatedCoins: List<String>,
    val shareURL: String,
    val source: String,
    val sourceLink: String,
    val title: String
)

