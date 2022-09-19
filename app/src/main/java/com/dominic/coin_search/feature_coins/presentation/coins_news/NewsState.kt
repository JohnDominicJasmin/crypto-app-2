package com.dominic.coin_search.feature_coins.presentation.coins_news

import androidx.compose.runtime.Immutable
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel


@Immutable
data class TrendingNews(
    val news: List<Pair<NewsModel, Boolean>> = emptyList(),
)
@Immutable
data class HandpickedNews(
    val news: List<Pair<NewsModel, Boolean>> = emptyList(),
)
@Immutable
data class LatestNews(
    val news: List<Pair<NewsModel, Boolean>> = emptyList(),
)
@Immutable
data class BullishNews(
    val news: List<Pair<NewsModel, Boolean>> = emptyList(),
)
@Immutable
data class BearishNews(
    val news: List<Pair<NewsModel, Boolean>> = emptyList(),
)

data class NewsState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val trendingNews: TrendingNews = TrendingNews(),
    val handpickedNews: HandpickedNews = HandpickedNews(),
    val latestNews: LatestNews = LatestNews(),
    val bullishNews: BullishNews = BullishNews(),
    val bearishNews: BearishNews = BearishNews(),
    val errorMessage: String = "",
    val hasInternet: Boolean = true,
)

