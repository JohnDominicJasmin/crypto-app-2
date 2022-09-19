package com.dominic.coin_search.feature_favorites.domain.use_case.add_news

import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import com.dominic.coin_search.feature_favorites.domain.repository.FavoritesRepository
import javax.inject.Inject

class AddNewsUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(news: NewsModel){
        repository.insertNews(news)
    }
}