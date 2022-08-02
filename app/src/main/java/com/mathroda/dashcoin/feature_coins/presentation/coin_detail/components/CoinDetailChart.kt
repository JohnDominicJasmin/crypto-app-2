package com.mathroda.dashcoin.feature_coins.presentation.coin_detail.components

import android.view.MotionEvent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.mathroda.dashcoin.R
import com.mathroda.dashcoin.core.util.CustomMarkerView
import com.mathroda.dashcoin.core.util.MyXAxisValueFormatter
import com.mathroda.dashcoin.core.util.MyYAxisValueFormatter
import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.utils.ChartScreenViewState
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.utils.setLineDataSet


@Composable
fun CoinDetailChart(
    modifier: Modifier,
    chartModel: ChartModel?,
    priceChange: Double,
    onChartGesture: (Float) -> Unit
) {
    val xAxisValueFormatter = remember { MyXAxisValueFormatter() }
    val dataSet = remember { mutableStateListOf<Entry>() }
    val context = LocalContext.current
    val lineDataChart = remember{ LineChart(context).apply {
        setNoDataText(" ")
        clear()

    }}
    LaunchedEffect(key1 = chartModel ){
        dataSet.clear()
        chartModel?.let { chartsValue ->
            chartsValue.chart.map { value ->
                for (i in value){
                    dataSet.add(addEntry(value[0], value[1]))
                }
            }
        }
        val lineDataSet = ChartScreenViewState().getLineDataSet(
                lineData = dataSet,
                label = "chart values",
                priceChange = priceChange,
                context = context,
            ).apply {
                mode = LineDataSet.Mode.LINEAR
            }
        lineDataChart.apply{
            invalidate()
            clear()
            setLineDataSet(lineDataSet)
            animateX(250, Easing.EaseOutBounce)
        }

    }


    AndroidView(
        factory = { contextFactory ->
            val markerView = CustomMarkerView(contextFactory, R.layout.marker_view)

            lineDataChart.apply {
                description.isEnabled = false
                xAxis.isEnabled = true
                axisLeft.textColor = Color.White.toArgb()
                axisLeft.valueFormatter = MyYAxisValueFormatter()
                axisRight.isEnabled = false
                axisLeft.axisLineColor = Color.Red.toArgb()
                axisLeft.setDrawAxisLine(false)
                xAxis.valueFormatter = xAxisValueFormatter
                xAxis.textColor = Color.White.toArgb()
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.axisLineColor = Color.Transparent.toArgb()
                xAxis.setDrawGridLines(false)
                legend.isEnabled = false
                setTouchEnabled(true)
                setScaleEnabled(false)
                setDrawGridBackground(false)
                setDrawBorders(false)
                setDrawMarkers(true)


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

                marker = markerView
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


fun addEntry(x: Float, y: Float) =
    BarEntry(x, y)


