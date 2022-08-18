package com.dominic.coin_search.feature_coins.presentation.coin_detail.components

import android.view.MotionEvent
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.dominic.coin_search.R
import com.dominic.coin_search.core.util.*
import com.dominic.coin_search.core.util.Formatters.millisToDate
import com.dominic.coin_search.core.util.Formatters.periodToTimeSpan
import com.dominic.coin_search.core.util.Formatters.toFormattedPrice
import com.dominic.coin_search.feature_coins.domain.models.chart.ChartModel
import com.dominic.coin_search.feature_coins.presentation.coin_detail.CoinDetailEvent
import com.dominic.coin_search.feature_coins.presentation.coin_detail.CoinDetailViewModel
import com.dominic.coin_search.feature_coins.presentation.coin_detail.utils.ChartLineDataSet
import com.dominic.coin_search.feature_coins.presentation.coin_detail.utils.setLineDataSet
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CoinDetailChart(
    modifier: Modifier,
    chartModel: ChartModel,
    chartPeriod: String,
    priceChange: Double,
    coinDetailViewModel: CoinDetailViewModel
) {
    val xAxisValueFormatter = remember { MyXAxisValueFormatter() }
    val dataSet = remember { mutableStateListOf<Entry>() }
    var lineDataSet by remember { mutableStateOf(LineDataSet(emptyList(), "")) }
    val context = LocalContext.current
    val lineDataChart = remember {
        LineChart(context).apply {
            setNoDataText(" ")
            clear()
        }
    }
    val markerView = remember { CustomMarkerView(context, R.layout.marker_view_chart) }
    val yAxisEntry by markerView.yEntry.observeAsState()
    val xAxisEntry by markerView.xEntry.observeAsState()

    LaunchedEffect(key1 = priceChange) {
        markerView.changeBackgroundCircleIndicator(if (priceChange < 0) R.drawable.circle_chart_negative else R.drawable.circle_chart_positive)
    }

    LaunchedEffect(key1 = yAxisEntry, key2 = xAxisEntry) {
        this.launch(Dispatchers.Main) {
            yAxisEntry?.let { coinDetailViewModel.onEvent(event = CoinDetailEvent.AddChartPrice(it.toFormattedPrice())) }
            xAxisEntry?.let { coinDetailViewModel.onEvent(event = CoinDetailEvent.AddChartDate( (it.toLong() * 1000).millisToDate("dd MMMM yyyy  HH:mm"))) }
            lineDataChart.apply {
                onChartGestureListener = object : OnChartGestureListener {
                    override fun onChartGestureStart(
                        me: MotionEvent?,
                        lastPerformedGesture: ChartTouchListener.ChartGesture?) {
                        lineDataChart.setDrawMarkers(true)
                        lineDataSet.isHighlightEnabled = true
                    }

                    override fun onChartGestureEnd(
                        me: MotionEvent?,
                        lastPerformedGesture: ChartTouchListener.ChartGesture?) {
                            coinDetailViewModel.onEvent(event = CoinDetailEvent.ClearChartDate)
                            coinDetailViewModel.onEvent(event = CoinDetailEvent.ClearChartPrice)
                        lineDataChart.setDrawMarkers(false)
                        lineDataSet.isHighlightEnabled = false
                    }

                    override fun onChartLongPressed(me: MotionEvent?) {

                    }

                    override fun onChartDoubleTapped(me: MotionEvent?) {

                    }

                    override fun onChartSingleTapped(me: MotionEvent?) {

                    }

                    override fun onChartFling(
                        me1: MotionEvent?, me2: MotionEvent?, velocityX: Float, velocityY: Float) {
                        coinDetailViewModel.onEvent(event = CoinDetailEvent.ClearChartDate)
                        coinDetailViewModel.onEvent(event = CoinDetailEvent.ClearChartPrice)
                        lineDataChart.setDrawMarkers(false)
                        lineDataSet.isHighlightEnabled = false
                    }

                    override fun onChartScale(me: MotionEvent?, scaleX: Float, scaleY: Float) {

                    }

                    override fun onChartTranslate(me: MotionEvent?, dX: Float, dY: Float) {
                    }
                }

            }
        }
    }




    LaunchedEffect(key1 = chartModel) {

        xAxisValueFormatter.format = periodToTimeSpan(chartPeriod)
        dataSet.clear()
        chartModel.chart.map { value ->
            for (i in value) {
                dataSet.add(BarEntry(value[0], value[1]))
            }
        }

         lineDataSet = ChartLineDataSet().getLineDataSet(
                lineData = dataSet,
                label = "chart values",
                priceChange = priceChange,
                context = context,
            ).apply {
                mode = LineDataSet.Mode.LINEAR
                lineWidth = 1.35f

            }

        lineDataChart.apply {
            fitScreen()
            invalidate()
            clear()
            setLineDataSet(lineDataSet)
            animateX(10)
        }

    }


    AndroidView(
        factory = { contextFactory ->


            lineDataChart.apply {
                description.isEnabled = false
                xAxis.isEnabled = true
                axisLeft.isEnabled = false
//                axisLeft.textColor = Color.White.toArgb()
//                axisLeft.textSize = 10f
//                axisLeft.valueFormatter = MyYAxisValueFormatter()
//                axisLeft.setDrawAxisLine(false)
//                axisLeft.setDrawGridLines(true)
//                axisLeft.yOffset = -3f
//                axisLeft.xOffset = -2f
//                axisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
//                axisLeft.axisLineColor = Black850.toArgb()
//                axisLeft.zeroLineColor = Black850.toArgb()
//                axisLeft.gridLineWidth = 0.4f
                axisRight.isEnabled = false

                xAxis.valueFormatter = xAxisValueFormatter
                xAxis.textColor = Color.White.toArgb()
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setDrawGridLines(false)
                legend.isEnabled = false
                xAxis.setCenterAxisLabels(true)
                setTouchEnabled(true)
                setScaleEnabled(false)
                setDrawGridBackground(false)
                isScaleYEnabled = false
                isScaleXEnabled = false
                setPinchZoom(false)
                setDrawBorders(false)
                viewPortHandler.setMaximumScaleX(10.5f)
                setDrawMarkers(true)
                isDragEnabled = true
                marker = markerView

            }
        },
        update = { lineChart ->

            lineChart.invalidate()
        },
        modifier = modifier
    )
}






