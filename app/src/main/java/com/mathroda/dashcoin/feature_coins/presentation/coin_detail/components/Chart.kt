package com.mathroda.dashcoin.feature_coins.presentation.coin_detail.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.utils.ChartScreenViewState
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.utils.setLineDataSet
import com.mathroda.dashcoin.ui.theme.TextWhite

@Composable
fun Chart(
    chartModel: ChartModel?,
    oneDayChange: Double,
    context: Context
) {


    val dataSet = mutableListOf<Entry>()
    chartModel?.let { chartsValue ->
        chartsValue.chart.map { value ->
            for (i in value){
                dataSet.add(addEntry(value[0].toFloat(), value[1].toFloat()))
            }
        }
    }

    AndroidView(
        factory = { contextFactory ->
            LineChart(contextFactory)
        },
        update = { lineChart ->

            val lineDataSet =
                ChartScreenViewState().getLineDataSet(
                    lineData = dataSet,
                    label = "chart values",
                    oneDayChange = oneDayChange,
                    context = context
                )


            lineChart.apply {
                description.isEnabled = false
                isDragEnabled = false
                xAxis.isEnabled = false
                axisLeft.setDrawAxisLine(false)
                axisLeft.textColor = TextWhite.toArgb()
                axisRight.isEnabled = false
                legend.isEnabled = false
                setTouchEnabled(false)
                setScaleEnabled(false)
                setDrawGridBackground(false)
                setDrawBorders(false)
                setLineDataSet(lineDataSet)
                invalidate()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(300.dp)
    )
}


fun addEntry(x: Float, y: Float) =
    Entry(x, y)