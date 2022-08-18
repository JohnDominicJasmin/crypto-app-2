package com.dominic.coin_search.feature_coins.domain.use_case.get_chart

import com.dominic.coin_search.feature_coins.domain.models.chart.ChartModel
import com.dominic.coin_search.feature_coins.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetChartUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    operator fun invoke(coinId: String, period: String): Flow<ChartModel> = flow {
            emit(repository.getChartsData(coinId, period))
    }
}