package com.dominic.coin_search.feature_intro_slider_screen.domain.use_case

import com.dominic.coin_search.feature_intro_slider_screen.domain.use_case.completed_intro_slider.CompletedIntroSliderUseCase
import com.dominic.coin_search.feature_intro_slider_screen.domain.use_case.read_intro_slider.UserCompletedWalkThroughUseCase

data class IntroSliderUseCase(
    val readIntroSliderUseCase: UserCompletedWalkThroughUseCase,
    val completedIntroSliderUseCase: CompletedIntroSliderUseCase
)
