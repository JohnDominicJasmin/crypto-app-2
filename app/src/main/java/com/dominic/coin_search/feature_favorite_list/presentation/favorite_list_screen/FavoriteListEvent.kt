package com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen

import com.dominic.coin_search.feature_coins.domain.models.coin.CoinDetailModel
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel

sealed class FavoriteListEvent {
    data class DeleteCoin(val coin: CoinDetailModel): FavoriteListEvent()
    data class AddCoin(val coin: CoinDetailModel): FavoriteListEvent()
    data class DeleteNews(val news: NewsModel): FavoriteListEvent()
    data class AddNews(val news: NewsModel): FavoriteListEvent()
}