package com.dominic.coin_search.feature_coins.domain.use_case.get_chart_period

import com.dominic.coin_search.feature_coins.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChartPeriodUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    suspend operator fun invoke(): Flow<String?> {
        return repository.getChartPeriod()
    }
}