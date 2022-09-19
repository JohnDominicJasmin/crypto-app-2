package com.dominic.coin_search.feature_favorites.presentation.favorites_screen

import com.dominic.coin_search.feature_coins.domain.models.coin.CoinDetailModel
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import javax.annotation.concurrent.Immutable


@Immutable
data class CoinDetails(val listOfCoins: List<CoinDetailModel> = emptyList())

@Immutable
data class News(val listOfNews: List<NewsModel> = emptyList())


data class FavoriteListState(
    val coinDetails: CoinDetails = CoinDetails(),
    val news: News = News(),

)
