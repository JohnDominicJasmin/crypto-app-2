package com.dominic.coin_search.core.di

import android.app.Application
import androidx.annotation.Keep
import androidx.room.Room
import com.dominic.coin_search.feature_favorite_list.data.local.FavoriteListDatabase
import com.dominic.coin_search.feature_favorite_list.data.repository.FavoriteListRepositoryImpl
import com.dominic.coin_search.feature_favorite_list.domain.repository.FavoriteListRepository
import com.dominic.coin_search.feature_favorite_list.domain.use_case.FavoriteUseCase
import com.dominic.coin_search.feature_favorite_list.domain.use_case.add_coin.AddCoinUseCase
import com.dominic.coin_search.feature_favorite_list.domain.use_case.add_news.AddNewsUseCase
import com.dominic.coin_search.feature_favorite_list.domain.use_case.delete_coin.DeleteCoinUseCase
import com.dominic.coin_search.feature_favorite_list.domain.use_case.delete_news.DeleteNewsUseCase
import com.dominic.coin_search.feature_favorite_list.domain.use_case.get_coins.GetAllCoinsUseCase
import com.dominic.coin_search.feature_favorite_list.domain.use_case.get_news.GetNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Keep
@Module
@InstallIn(SingletonComponent::class)
object FavoritesModule {


    @Provides
    @Singleton
    fun providesDashCoinDatabase(app: Application): FavoriteListDatabase {
        return Room.databaseBuilder(
            app,
            FavoriteListDatabase::class.java,
            FavoriteListDatabase.DATABASE_NAME
        ).build()
    }
    @Provides
    @Singleton
    fun providesFavoritesRepository(db: FavoriteListDatabase): FavoriteListRepository {
        return FavoriteListRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun providesFavoriteUseCase(repository: FavoriteListRepository): FavoriteUseCase {
        return FavoriteUseCase(
            addCoin =  AddCoinUseCase(repository),
            deleteCoin = DeleteCoinUseCase(repository),
            getCoins = GetAllCoinsUseCase(repository),
            addNews = AddNewsUseCase(repository),
            deleteNews = DeleteNewsUseCase(repository),
            getNews = GetNewsUseCase(repository)

        )
    }
}