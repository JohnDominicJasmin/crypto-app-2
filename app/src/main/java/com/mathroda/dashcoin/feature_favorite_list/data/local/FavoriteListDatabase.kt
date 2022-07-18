package com.mathroda.dashcoin.feature_favorite_list.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mathroda.dashcoin.core.util.Constants
import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel

@Database(
    entities = [CoinDetailModel::class],
    version = 1,
    exportSchema = false
)
abstract class FavoriteListDatabase: RoomDatabase() {

    abstract val dao: FavoriteListDao

    companion object {
      const val DATABASE_NAME = Constants.DATABASE_NAME
    }
}