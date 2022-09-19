package com.dominic.coin_search.feature_favorites.domain.repository

import com.dominic.coin_search.feature_coins.domain.models.coin.CoinDetailModel
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun insertCoin(coins: CoinDetailModel)

    suspend fun deleteCoin(coins: CoinDetailModel)

    fun getAllCoins(): Flow<List<CoinDetailModel>>

    suspend fun insertNews(news: NewsModel)

    fun getAllNews(): Flow<List<NewsModel>>

    suspend fun deleteNews(news: NewsModel)
}