package com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen

import com.dominic.coin_search.feature_coins.domain.models.coin.CoinDetailModel
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel

data class FavoriteListState(
    val coins: List<CoinDetailModel> = emptyList(),
    val news: List<NewsModel> = emptyList(),

)
