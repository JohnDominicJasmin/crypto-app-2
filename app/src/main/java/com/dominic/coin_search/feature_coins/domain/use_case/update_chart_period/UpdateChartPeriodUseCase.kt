package com.dominic.coin_search.feature_coins.domain.use_case.update_chart_period

import com.dominic.coin_search.feature_coins.domain.repository.CoinRepository
import javax.inject.Inject

class UpdateChartPeriodUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(period: String) {
        repository.updateChartPeriod(period)
    }
}