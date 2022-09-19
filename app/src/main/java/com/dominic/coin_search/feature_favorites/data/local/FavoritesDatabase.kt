package com.dominic.coin_search.feature_favorites.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dominic.coin_search.core.util.Constants
import com.dominic.coin_search.feature_coins.domain.models.coin.CoinDetailModel
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel

@Database(
    entities = [CoinDetailModel::class, NewsModel::class],
    version = 1,
    exportSchema = false
)
abstract class FavoritesDatabase: RoomDatabase() {

    abstract val dao: FavoritesDao

    companion object {
      const val DATABASE_NAME = Constants.DATABASE_NAME
    }
}