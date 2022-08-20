package com.dominic.coin_search.feature_coins.presentation.coins_news

import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel

data class NewsState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val trendingNews: List<Pair<NewsModel, Boolean>> = emptyList(),
    val handpickedNews: List<Pair<NewsModel, Boolean>> = emptyList(),
    val latestNews: List<Pair<NewsModel, Boolean>> = emptyList(),
    val bullishNews: List<Pair<NewsModel, Boolean>> = emptyList(),
    val bearishNews: List<Pair<NewsModel, Boolean>> = emptyList(),
    val errorMessage: String = "",
    val hasInternet: Boolean = true,
    val searchQuery: String = ""
)

