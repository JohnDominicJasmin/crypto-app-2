package com.mathroda.dashcoin.feature_coins.domain.use_case.get_news

import com.mathroda.dashcoin.feature_coins.domain.models.NewsDetailModel
import com.mathroda.dashcoin.feature_coins.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    operator fun invoke(filter: String): Flow<List<NewsDetailModel>> = flow {
       emit(repository.getNews(filter))
    }
}