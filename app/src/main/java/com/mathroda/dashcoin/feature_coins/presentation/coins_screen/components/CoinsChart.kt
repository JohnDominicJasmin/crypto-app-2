package com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.mathroda.dashcoin.R
import com.mathroda.dashcoin.core.util.Constants.LAST_HOURS
import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.components.addEntry
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.utils.ChartScreenViewState
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.utils.getCompatDrawable
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.utils.setLineDataSet
import com.mathroda.dashcoin.ui.theme.TextWhite


@Composable
fun CoinsChart(
    modifier: Modifier,
    chartModel: ChartModel?,
    priceChange: Double,
    context: Context
) {

    val dataSet = mutableListOf<Entry>()
    chartModel?.let { chartsValue ->
        chartsValue.chart.takeLast(LAST_HOURS).map { value ->
            for (i in value) {
                dataSet.add(addEntry(value[0], value[1]))
            }
        }
    }

    AndroidView(
        factory = { contextFactory ->



            LineChart(contextFactory).apply {

                val lineDataSet =
                    ChartScreenViewState().getLineDataSet(
                        lineData = dataSet,
                        label = "chart values",
                        priceChange = priceChange,
                        context = context,

                        ).apply {
                        fillDrawable =
                            context.getCompatDrawable(R.drawable.background_transparent_chart)
                        lineWidth = 1.0f
                    }
                description.isEnabled = false
                isDragEnabled = false
                xAxis.isEnabled = false
                axisLeft.setDrawAxisLine(false)
                axisLeft.textColor = TextWhite.toArgb()
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

            lineChart.apply {
                invalidate()
            }


        },
        modifier = modifier
    )
}
