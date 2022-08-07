package com.dominic.coin_search.feature_coins.presentation.coins_screen.components

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.dominic.coin_search.core.util.Constants.LAST_HOURS
import com.dominic.coin_search.feature_coins.domain.models.ChartModel
import com.dominic.coin_search.feature_coins.presentation.coin_detail.components.addEntry
import com.dominic.coin_search.feature_coins.presentation.coin_detail.utils.ChartLineDataSet
import com.dominic.coin_search.feature_coins.presentation.coin_detail.utils.getCompatDrawable
import com.dominic.coin_search.feature_coins.presentation.coin_detail.utils.setLineDataSet
import com.dominic.coin_search.ui.theme.White800
import com.dominic.coin_search.R

@Composable
fun CoinsChart(
    modifier: Modifier,
    chartModel: ChartModel?,
    priceChange: Double,
) {


    AndroidView(
        factory = { contextFactory ->
            val dataSet = mutableListOf<Entry>()
            chartModel?.let { chartsValue ->
                chartsValue.chart.takeLast(LAST_HOURS).map { value ->
                    for (i in value) {
                        dataSet.add(addEntry(value[0], value[1]))
                    }
                }
            }
            val lineDataSet =
                ChartLineDataSet().getLineDataSet(
                    lineData = dataSet,
                    label = "chart values",
                    priceChange = priceChange,
                    context = contextFactory,

                    ).apply {
                    fillDrawable =
                        contextFactory.getCompatDrawable(R.drawable.background_transparent_chart)
                    lineWidth = 1.0f
                }
            LineChart(contextFactory).apply {

                description.isEnabled = false
                isDragEnabled = false
                xAxis.isEnabled = false
                axisLeft.setDrawAxisLine(false)
                axisLeft.textColor = White800.toArgb()
                axisLeft.isEnabled = false
                axisRight.isEnabled = false
                legend.isEnabled = false
                isDoubleTapToZoomEnabled = false
                isHighlightPerDragEnabled = false
                setTouchEnabled(false)
                zoom(1f, 1f, 100f, 1f)
                setScaleEnabled(false)
                setDrawGridBackground(false)
                setDrawBorders(false)
                setDrawMarkers(false)
                setNoDataText("No Data Available")
                setLineDataSet(lineDataSet, 1)
            }

        },
        update = { lineChart ->
            lineChart.invalidate()
        },
        modifier = modifier
    )
}
