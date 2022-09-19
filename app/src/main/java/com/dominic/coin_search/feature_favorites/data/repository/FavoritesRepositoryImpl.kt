package com.dominic.coin_search.feature_favorites.data.repository

import com.dominic.coin_search.feature_favorites.data.local.FavoritesDao
import com.dominic.coin_search.feature_coins.domain.models.coin.CoinDetailModel
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import com.dominic.coin_search.feature_favorites.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val dao: FavoritesDao): FavoritesRepository{


    override suspend fun insertCoin(coins: CoinDetailModel) {
        dao.insertCoin(coins)
    }

    override suspend fun deleteCoin(coins: CoinDetailModel) {
        dao.deleteCoin(coins)
    }

    override fun getAllCoins(): Flow<List<CoinDetailModel>> {
        return dao.getAllCoins()
    }

    override suspend fun insertNews(news: NewsModel) {
        dao.insertNews(news)
    }

    override fun getAllNews(): Flow<List<NewsModel>> {
       return dao.getAllNews()
    }

    override suspend fun deleteNews(news: NewsModel) {
        dao.deleteNews(news)
    }
}