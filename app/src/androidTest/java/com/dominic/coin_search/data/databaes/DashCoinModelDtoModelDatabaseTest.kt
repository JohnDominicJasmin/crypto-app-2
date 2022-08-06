package com.dominic.coin_search.data.databaes

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.dominic.coin_search.feature_favorite_list.data.local.FavoriteListDatabase
import com.dominic.coin_search.feature_favorite_list.data.local.FavoriteListDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class DashCoinModelDtoModelDatabaseTest {
    private lateinit var favoriteListDao: FavoriteListDao
    private lateinit var favoriteListDatabase: FavoriteListDatabase


    @Before
    fun setUp() {
            val context = ApplicationProvider.getApplicationContext<Context>()
            favoriteListDatabase = Room.inMemoryDatabaseBuilder(
                context,
                FavoriteListDatabase::class.java
            ).allowMainThreadQueries().build()

        favoriteListDao = favoriteListDatabase.dao
    }

    @After
    fun tearDown() {
        favoriteListDatabase.close()
    }

    @Test
    fun insertCoinToDatabase() = runTest {
        val coinById = FakeDataTest.coinDetailModel
        favoriteListDao.insertCoin(coinById)

        favoriteListDao.getAllCoins().onEach {
            assertThat(it).isEqualTo(coinById)
        }
    }

    @Test
    fun deleteCoinToDatabase() = runTest {
        val coinById = FakeDataTest.coinDetailModel
        favoriteListDao.insertCoin(coinById)
        favoriteListDao.deleteCoin(coinById)

        favoriteListDao.getAllCoins().onEach {
            assertThat(it).isEmpty()
        }
    }
}