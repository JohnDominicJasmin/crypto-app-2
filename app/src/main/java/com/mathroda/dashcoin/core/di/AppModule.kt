package com.mathroda.dashcoin.core.di

import android.app.Application
import androidx.room.Room
import com.mathroda.dashcoin.core.util.Constants
import com.mathroda.dashcoin.feature_favorite_list.data.data_source.local.FavoriteListDatabase
import com.mathroda.dashcoin.feature_coins.data.data_source.remote.DashCoinApi
import com.mathroda.dashcoin.feature_coins.data.repository.CoinRepositoryImpl
import com.mathroda.dashcoin.feature_coins.domain.repository.CoinRepository
import com.mathroda.dashcoin.feature_coins.domain.use_case.CoinUseCases
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_chart.GetChartUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_coin.GetCoinUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_coins.GetCoinsUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_news.GetNewsUseCase
import com.mathroda.dashcoin.feature_favorite_list.data.repository.FavoriteListRepositoryImpl
import com.mathroda.dashcoin.feature_favorite_list.domain.repository.FavoriteListRepository
import com.mathroda.dashcoin.feature_favorite_list.domain.use_case.FavoriteUseCase
import com.mathroda.dashcoin.feature_favorite_list.domain.use_case.add_coin.AddCoinUseCase
import com.mathroda.dashcoin.feature_favorite_list.domain.use_case.delete_coin.DeleteCoinUseCase
import com.mathroda.dashcoin.feature_favorite_list.domain.use_case.get_all.GetAllCoinsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDashCoinApi(): DashCoinApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DashCoinApi::class.java)
    }

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
    fun providesCoinRepository(api: DashCoinApi): CoinRepository {
        return CoinRepositoryImpl(api)
    }



    @Provides
    @Singleton
    fun providesSavedRepository(db: FavoriteListDatabase): FavoriteListRepository{
        return FavoriteListRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun providesCoinUseCase(repository: CoinRepository): CoinUseCases {
        return CoinUseCases(
            getCoins = GetCoinsUseCase(repository),
            getCoin = GetCoinUseCase(repository),
            getChart = GetChartUseCase(repository),
            getNews = GetNewsUseCase(repository),


        )
    }


    @Provides
    @Singleton
    fun providesSavedUseCase(repository: FavoriteListRepository): FavoriteUseCase {
        return FavoriteUseCase(
            addCoin =  AddCoinUseCase(repository),
            deleteCoin = DeleteCoinUseCase(repository),
            getAllCoins = GetAllCoinsUseCase(repository)
        )
    }

}