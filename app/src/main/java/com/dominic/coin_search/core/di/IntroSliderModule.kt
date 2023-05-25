package com.dominic.coin_search.core.di

import android.content.Context
import com.dominic.coin_search.feature_intro_slider_screen.data.repository.IntroSliderRepositoryImpl
import com.dominic.coin_search.feature_intro_slider_screen.domain.repository.IntroSliderRepository
import com.dominic.coin_search.feature_intro_slider_screen.domain.use_case.IntroSliderUseCase
import com.dominic.coin_search.feature_intro_slider_screen.domain.use_case.completed_intro_slider.CompletedIntroSliderUseCase
import com.dominic.coin_search.feature_intro_slider_screen.domain.use_case.read_intro_slider.UserCompletedWalkThroughUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object IntroSliderModule {


    @Provides
    @Singleton
    fun provideIntroSliderRepository(@ApplicationContext context: Context): IntroSliderRepository {
        return IntroSliderRepositoryImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideIntroSliderUseCase(repository: IntroSliderRepository): IntroSliderUseCase
        = IntroSliderUseCase(
        completedIntroSliderUseCase = CompletedIntroSliderUseCase(repository),
        readIntroSliderUseCase = UserCompletedWalkThroughUseCase(repository))


}