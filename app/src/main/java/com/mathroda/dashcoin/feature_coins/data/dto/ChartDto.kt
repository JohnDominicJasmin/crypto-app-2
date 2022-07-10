package com.mathroda.dashcoin.feature_coins.data.dto

import androidx.annotation.Keep
import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel


data class ChartDto(
    val chart: List<List<Float>>,
)

