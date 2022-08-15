package com.dominic.coin_search.feature_coins.domain.models

data class NewsDetailModel(
    val description: String = "",
    val id: String = "",
    val imgURL: String = "",
    val link: String = "",
    val relatedCoins: List<Any> = emptyList(),
    val shareURL: String = "",
    val source: String = "",
    val sourceLink: String = "",
    val title: String = ""
)
