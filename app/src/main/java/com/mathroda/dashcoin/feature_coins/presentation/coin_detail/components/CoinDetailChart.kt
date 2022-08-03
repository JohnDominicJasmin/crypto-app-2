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
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
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
import com.mathroda.dashcoin.feature_coins.domain.models.ChartTimeSpan
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.utils.ChartLineDataSet
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.utils.setLineDataSet
import com.mathroda.dashcoin.ui.theme.Black850


@Composable
fun CoinDetailChart(
    modifier: Modifier,
    chartModel: ChartModel,
    chartPeriod: String,
    priceChange: Double,
    onChangeYAxisValue: (Float) -> Unit,
    onChangeXAxisValue: (Float) -> Unit
) {
    val xAxisValueFormatter = remember { MyXAxisValueFormatter() }
    val dataSet = remember { mutableStateListOf<Entry>() }
    val context = LocalContext.current
    val lineDataChart = remember{ LineChart(context).apply {
        setNoDataText(" ")
        clear()

    }}



    LaunchedEffect(key1 = chartModel ){

        xAxisValueFormatter.format = when(chartPeriod){
            ChartTimeSpan.OneDay.value -> {
                "HH:mm"
            }
            ChartTimeSpan.OneWeek.value -> {
                "EEE"
            }
            ChartTimeSpan.OneMonth.value, ChartTimeSpan.ThreeMonths.value, ChartTimeSpan.SixMonths.value -> {
                "dd MMM"
            }
            ChartTimeSpan.OneYear.value -> {
                "MMM"
            }
            else -> {
                "MMM dd"
            }
        }


        dataSet.clear()

            chartModel.chart.map { value ->
                for (i in value){
                    dataSet.add(addEntry(value[0], value[1]))
                }
            }
        val lineDataSet = ChartLineDataSet().getLineDataSet(
                lineData = dataSet,
                label = "chart values",
                priceChange = priceChange,
                context = context,
            ).apply {
                mode = LineDataSet.Mode.LINEAR
                lineWidth = 1.35f
            }
        lineDataChart.apply{
            fitScreen()
            invalidate()
            clear()
            setLineDataSet(lineDataSet)
            animateX(1000)
        }

    }


    AndroidView(
        factory = { contextFactory ->
            val markerView = CustomMarkerView(contextFactory, R.layout.marker_view)

            lineDataChart.apply {
                description.isEnabled = false
                xAxis.isEnabled = true
                axisLeft.textColor = Color.White.toArgb()
                axisLeft.textSize = 10f
                axisLeft.valueFormatter = MyYAxisValueFormatter()
                axisRight.isEnabled = false
                axisLeft.setDrawAxisLine(false)
                axisLeft.setDrawGridLines(true)
                axisLeft.yOffset = -3f
                axisLeft.xOffset = -2f
                axisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
                axisLeft.axisLineColor = Black850.toArgb()
                axisLeft.zeroLineColor = Black850.toArgb()
                axisLeft.gridLineWidth = 0.4f
                xAxis.valueFormatter = xAxisValueFormatter
                xAxis.textColor = Color.White.toArgb()
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setDrawGridLines(false)
                legend.isEnabled = false
                setTouchEnabled(true)
                setScaleEnabled(true)
                setDrawGridBackground(false)
                isScaleYEnabled = false
                setPinchZoom(true)
                setDrawBorders(false)
                viewPortHandler.setMaximumScaleX(10.5f)
                setDrawMarkers(true)


                onChartGestureListener = object: OnChartGestureListener{
                    override fun onChartGestureStart(
                        me: MotionEvent?,
                        lastPerformedGesture: ChartTouchListener.ChartGesture?) {

                        onChangeYAxisValue(markerView.yEntry)
                        onChangeXAxisValue(markerView.xEntry)
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


