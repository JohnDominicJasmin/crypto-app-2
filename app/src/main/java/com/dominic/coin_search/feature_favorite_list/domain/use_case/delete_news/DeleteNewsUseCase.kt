package com.dominic.coin_search.feature_favorite_list.domain.use_case.delete_news

import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import com.dominic.coin_search.feature_favorite_list.domain.repository.FavoriteListRepository
import javax.inject.Inject

class DeleteNewsUseCase@Inject constructor(
    private val repository: FavoriteListRepository
) {
    suspend operator fun invoke(newsModel: NewsModel){
        repository.deleteNews(newsModel)
    }
}