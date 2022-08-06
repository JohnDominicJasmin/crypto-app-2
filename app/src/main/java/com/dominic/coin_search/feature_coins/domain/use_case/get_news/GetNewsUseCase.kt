package com.dominic.coin_search.feature_coins.domain.use_case.get_news

import com.dominic.coin_search.feature_coins.domain.models.NewsDetailModel
import com.dominic.coin_search.feature_coins.domain.repository.CoinRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: CoinRepository) {

    suspend operator fun invoke(filter: String): List<NewsDetailModel> {
       return repository.getNews(filter)
    }
}