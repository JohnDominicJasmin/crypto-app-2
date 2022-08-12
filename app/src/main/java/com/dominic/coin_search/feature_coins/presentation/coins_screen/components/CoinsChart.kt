package com.dominic.coin_search.feature_coins.presentation.coins_screen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.dominic.coin_search.R
import com.dominic.coin_search.core.util.Constants.LAST_HOURS
import com.dominic.coin_search.feature_coins.domain.models.ChartModel
import com.dominic.coin_search.feature_coins.presentation.coin_detail.utils.ChartLineDataSet
import com.dominic.coin_search.feature_coins.presentation.coin_detail.utils.getCompatDrawable
import com.dominic.coin_search.feature_coins.presentation.coin_detail.utils.setLineDataSet
import com.dominic.coin_search.ui.theme.White800
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry

@Composable
fun CoinsChart(
    modifier: Modifier,
    chartModel: ChartModel?,
    priceChange: Double,
) {


    AndroidView(
        factory = { contextFactory ->
            val dataSet = mutableListOf<Entry>()
            chartModel?.chart?.takeLast(LAST_HOURS)?.map { value ->
                for (i in value) {
                    dataSet.add(BarEntry(value[0], value[1]))
                }
            }

            LineChart(contextFactory).apply {
                val lineDataSet =
                    ChartLineDataSet().getLineDataSet(
                        lineData = dataSet,
                        label = "chart values",
                        priceChange = priceChange,
                        context = context,

                        ).apply {
                        fillDrawable =
                            context.getCompatDrawable(R.drawable.background_transparent_chart)
                        lineWidth = 1.5f
                    }
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
                invalidate()

            }

        },
        update = { lineChart ->
        },
        modifier = modifier
    )
}
