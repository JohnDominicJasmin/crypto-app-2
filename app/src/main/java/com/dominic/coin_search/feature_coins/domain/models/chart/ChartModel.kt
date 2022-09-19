package com.dominic.coin_search.feature_coins.domain.models.chart

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
@Immutable
data class ChartModel(
    val chart: List<List<Float>> = emptyList()
)
