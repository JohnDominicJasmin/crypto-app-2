package com.dominic.coin_search.feature_favorites.domain.use_case.delete_news

import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import com.dominic.coin_search.feature_favorites.domain.repository.FavoritesRepository
import javax.inject.Inject

class DeleteNewsUseCase@Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(newsModel: NewsModel){
        repository.deleteNews(newsModel)
    }
}