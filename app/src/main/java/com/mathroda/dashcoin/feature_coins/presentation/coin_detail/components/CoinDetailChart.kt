package com.mathroda.dashcoin.feature_coins.presentation.coin_detail.components

import android.content.Context
import android.view.MotionEvent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.mathroda.dashcoin.R
import com.mathroda.dashcoin.core.util.CustomMarkerView
import com.mathroda.dashcoin.core.util.MyYAxisValueFormatter
import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.utils.ChartScreenViewState
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.utils.setLineDataSet
import com.mathroda.dashcoin.ui.theme.TextWhite


@Composable
fun CoinDetailChart(
    modifier: Modifier,
    chartModel: ChartModel?,
    oneDayChange: Double,
    context: Context,
    onChartGesture:(Float) -> Unit
) {

    val markerView = CustomMarkerView(context, R.layout.marker_view)
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
                onChartGestureListener = object: OnChartGestureListener{
                    override fun onChartGestureStart(
                        me: MotionEvent?,
                        lastPerformedGesture: ChartTouchListener.ChartGesture?) {

                        onChartGesture(markerView.yEntry)
                    }

                    override fun onChartGestureEnd(
                        me: MotionEvent?,
                        lastPerformedGesture: ChartTouchListener.ChartGesture?) {

                    }

                    override fun onChartLongPressed(me: MotionEvent?) {
                    }

                    override fun onChartDoubleTapped(me: MotionEvent?) {

                    }

                    override fun onChartSingleTapped(me: MotionEvent?) {

                    }

                    override fun onChartFling(
                        me1: MotionEvent?,
                        me2: MotionEvent?,
                        velocityX: Float,
                        velocityY: Float) {

                    }

                    override fun onChartScale(me: MotionEvent?, scaleX: Float, scaleY: Float) {

                    }

                    override fun onChartTranslate(me: MotionEvent?, dX: Float, dY: Float) {
                    }
                }
                description.isEnabled = false
                xAxis.isEnabled = false
                axisLeft.textColor = TextWhite.toArgb()
                axisLeft.valueFormatter = MyYAxisValueFormatter()
                axisRight.isEnabled = false
                axisLeft.axisLineColor = Color.Red.toArgb()
                axisLeft.setDrawAxisLine(false)

                legend.isEnabled = false
                setTouchEnabled(true)
                setScaleEnabled(false)
                setDrawGridBackground(false)
                animateX(1, Easing.EaseInOutBounce)
                setDrawBorders(false)
                setDrawMarkers(true)

                marker = markerView
            }
        },
        update = { lineChart ->

             val lineDataSet =
                ChartScreenViewState().getLineDataSet(
                    lineData = dataSet,
                    label = "chart values",
                    oneDayChange = oneDayChange,
                    context = context,
                ).apply {
                   mode = LineDataSet.Mode.LINEAR


                }


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


