package com.mathroda.dashcoin.feature_coins.presentation.coin_detail.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.mathroda.dashcoin.R
import com.mathroda.dashcoin.core.util.CustomMarkerView
import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.utils.ChartScreenViewState
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.utils.setLineDataSet
import com.mathroda.dashcoin.ui.theme.TextWhite


@Composable
fun CoinDetailChart(
    modifier: Modifier,
    chartModel: ChartModel?,
    oneDayChange: Double,
    context: Context
) {

    val mv = CustomMarkerView(context, R.layout.marker_view)
    val dataSet = mutableListOf<Entry>()
    chartModel?.let { chartsValue ->
        chartsValue.chart.map { value ->
            for (i in value){
                dataSet.add(addEntry(value[0], value[1]))
            }
        }
    }

    AndroidView(
        factory = { contextFactory ->
            LineChart(contextFactory).apply {
                description.isEnabled = false
                isDragEnabled = true
                xAxis.isEnabled = false
                axisLeft.setDrawAxisLine(false)
                axisLeft.textColor = TextWhite.toArgb()
                axisRight.isEnabled = false
                legend.isEnabled = false
                isDoubleTapToZoomEnabled = true
                isHighlightPerDragEnabled = true
                setTouchEnabled(true)
                setScaleEnabled(true)
                setDrawGridBackground(false)
                animateX(1)
                setDrawBorders(false)
                setDrawMarkers(true)
                marker = mv
            }
        },
        update = { lineChart ->

            val lineDataSet =
                ChartScreenViewState().getLineDataSet(
                    lineData = dataSet,
                    label = "chart values",
                    oneDayChange = oneDayChange,
                    context = context,
                )


            lineChart.apply {

                setLineDataSet(lineDataSet)
                setNoDataText("No Data Available")


                invalidate()
            }
        },
        modifier = modifier
    )
}


fun addEntry(x: Float, y: Float) =
    Entry(x, y)


