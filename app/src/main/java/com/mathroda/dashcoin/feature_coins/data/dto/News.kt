package com.mathroda.dashcoin.feature_coins.data.dto

import com.mathroda.dashcoin.feature_coins.domain.models.NewsDetailModel

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
    val relatedCoins: List<Any>,
    val shareURL: String,
    val source: String,
    val sourceLink: String,
    val title: String
)

