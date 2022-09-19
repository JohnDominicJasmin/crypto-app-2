package com.dominic.coin_search.feature_favorites.data.local

import androidx.room.*
import com.dominic.coin_search.feature_coins.domain.models.coin.CoinDetailModel
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coinDetailModel: CoinDetailModel)

    @Delete
    suspend fun deleteCoin(coinDetailModel: CoinDetailModel)

    @Query("SELECT * FROM CoinDetailModel")
    fun getAllCoins(): Flow<List<CoinDetailModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsModel: NewsModel)

    @Delete
    suspend fun deleteNews(newsModel: NewsModel)

    @Query("SELECT * FROM NewsModel")
    fun getAllNews(): Flow<List<NewsModel>>
}