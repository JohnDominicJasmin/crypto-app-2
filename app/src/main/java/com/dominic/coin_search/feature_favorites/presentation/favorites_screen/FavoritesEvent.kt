package com.dominic.coin_search.feature_favorites.presentation.favorites_screen

import com.dominic.coin_search.feature_coins.domain.models.coin.CoinDetailModel
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel

sealed class FavoritesEvent {
    data class DeleteCoin(val coin: CoinDetailModel): FavoritesEvent()
    data class AddCoin(val coin: CoinDetailModel): FavoritesEvent()
    data class DeleteNews(val news: NewsModel): FavoritesEvent()
    data class AddNews(val news: NewsModel): FavoritesEvent()
}