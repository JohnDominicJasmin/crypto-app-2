package com.mathroda.dashcoin.feature_coins.domain.use_case.get_news

import com.mathroda.dashcoin.feature_coins.domain.models.NewsDetailModel
import com.mathroda.dashcoin.feature_coins.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: CoinRepository) {

    suspend operator fun invoke(filter: String): List<NewsDetailModel> {
       return repository.getNews(filter)
    }
}