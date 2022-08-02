package com.mathroda.dashcoin.feature_coins.domain.use_case.update_chart_period

import com.mathroda.dashcoin.feature_coins.domain.repository.CoinRepository
import javax.inject.Inject

class UpdateChartPeriodUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(period: String) {
        repository.updateChartPeriod(period)
    }
}