package com.dominic.coin_search.feature_favorites.domain.use_case.get_news

import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import com.dominic.coin_search.feature_favorites.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
     operator fun invoke(): Flow<List<NewsModel>> {
        return repository.getAllNews()
    }
}