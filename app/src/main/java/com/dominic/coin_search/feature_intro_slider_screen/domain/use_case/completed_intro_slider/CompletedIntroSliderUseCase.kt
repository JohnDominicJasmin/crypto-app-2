package com.dominic.coin_search.feature_intro_slider_screen.domain.use_case.completed_intro_slider
import com.dominic.coin_search.feature_intro_slider_screen.domain.repository.IntroSliderRepository

class CompletedIntroSliderUseCase(
    private val repository: IntroSliderRepository) {

    suspend operator fun invoke(){
        repository.setUserCompletedWalkThrough()
    }
}