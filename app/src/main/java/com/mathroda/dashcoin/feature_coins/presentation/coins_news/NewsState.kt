package com.mathroda.dashcoin.feature_coins.presentation.coins_news

import com.mathroda.dashcoin.feature_coins.domain.models.NewsDetailModel

data class NewsState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val handPickedNews: List<NewsDetailModel> = emptyList(),
    val trendingNews: List<NewsDetailModel> = emptyList(),
    val latestNews: List<NewsDetailModel> = emptyList(),
    val bullishNews: List<NewsDetailModel> = emptyList(),
    val bearishNews: List<NewsDetailModel> = emptyList(),
    val errorMessage: String = "",
    val hasInternet: Boolean = true,
    val searchQuery: String = ""
)
