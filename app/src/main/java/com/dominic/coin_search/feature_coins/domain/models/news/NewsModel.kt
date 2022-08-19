package com.dominic.coin_search.feature_coins.domain.models.news

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsModel(
    @PrimaryKey
    val id: String = "",
    val description: String = "",
    val imgURL: String? = null,
    val link: String? = null,
    val source: String = "",
    val title: String = "",
    val feedDate: Long = 0L
)
