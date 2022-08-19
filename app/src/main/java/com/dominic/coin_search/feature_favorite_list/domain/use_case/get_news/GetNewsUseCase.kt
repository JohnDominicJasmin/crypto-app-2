package com.dominic.coin_search.feature_favorite_list.domain.use_case.get_news

import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import com.dominic.coin_search.feature_favorite_list.domain.repository.FavoriteListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: FavoriteListRepository
) {
     operator fun invoke(): Flow<List<NewsModel>> {
        return repository.getAllNews()
    }
}