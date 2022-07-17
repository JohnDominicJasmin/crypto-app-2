package com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.mathroda.dashcoin.R
import com.mathroda.dashcoin.core.util.Constants.LAST_FIVE_HOURS
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
    oneDayChange: Double,
    context: Context
) {

    val dataSet = mutableListOf<Entry>()
    chartModel?.let { chartsValue ->
        chartsValue.chart.takeLast(LAST_FIVE_HOURS).map { value ->
            for (i in value){
                dataSet.add(addEntry(value[0], value[1]))
            }
        }
    }

    AndroidView(
        factory = { contextFactory ->
           LineChart(contextFactory).apply {
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
               zoom(1f,1f,100f,1f)
               setScaleEnabled(false)
               animateX(1)
               setDrawGridBackground(false)
               setDrawBorders(false)
               setDrawMarkers(false)
           }

        },
        update = { lineChart ->

            val lineDataSet =
                ChartScreenViewState().getLineDataSet(
                    lineData = dataSet,
                    label = "chart values",
                    oneDayChange = oneDayChange,
                    context = context,

                ).apply{
                    fillDrawable = context.getCompatDrawable(R.drawable.background_transparent_chart)
                    lineWidth =  1.4f
                }

            lineChart.apply{
                setLineDataSet(lineDataSet)
                setNoDataText("No Data Available")
                invalidate()
            }


        },
        modifier = modifier
    )
}
