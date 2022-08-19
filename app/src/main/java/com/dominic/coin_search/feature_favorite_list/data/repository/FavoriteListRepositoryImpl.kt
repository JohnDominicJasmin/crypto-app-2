package com.dominic.coin_search.feature_favorite_list.data.repository

import com.dominic.coin_search.feature_favorite_list.data.local.FavoriteListDao
import com.dominic.coin_search.feature_coins.domain.models.coin.CoinDetailModel
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import com.dominic.coin_search.feature_favorite_list.domain.repository.FavoriteListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteListRepositoryImpl @Inject constructor(
    private val dao: FavoriteListDao): FavoriteListRepository{


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