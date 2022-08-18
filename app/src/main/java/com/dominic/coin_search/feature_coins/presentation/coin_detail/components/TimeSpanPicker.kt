package com.dominic.coin_search.feature_coins.presentation.coin_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dominic.coin_search.feature_coins.domain.models.chart.ChartTimeSpan
import com.dominic.coin_search.ui.theme.GreenBlue600




@Composable
fun TimeSpanPicker(
    modifier: Modifier = Modifier,
    selectedTimeSpan: String ,
    onTimeSpanSelected: (ChartTimeSpan) -> Unit = {}
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        item{

            with(ChartTimeSpan.OneDay) {
                TimeSpanChip(
                    time = "1D",
                    isSelected = selectedTimeSpan == value
                ) {
                    onTimeSpanSelected(this)
                }
            }

            with(ChartTimeSpan.OneWeek) {
                TimeSpanChip(
                    time = value.uppercase(),
                    isSelected = selectedTimeSpan == value
                ) {
                    onTimeSpanSelected(this)
                }
            }

            with(ChartTimeSpan.OneMonth) {
                TimeSpanChip(
                    time = value.uppercase(),
                    isSelected = selectedTimeSpan == value
                ) {
                    onTimeSpanSelected(this)
                }
            }

            with(ChartTimeSpan.ThreeMonths) {
                TimeSpanChip(
                    time = value.uppercase(),
                    isSelected = selectedTimeSpan == value
                ) {
                    onTimeSpanSelected(this)
                }
            }

            with(ChartTimeSpan.SixMonths) {
                TimeSpanChip(
                    time = value.uppercase(),
                    isSelected = selectedTimeSpan == value
                ) {
                    onTimeSpanSelected(this)
                }
            }

            with(ChartTimeSpan.OneYear) {
                TimeSpanChip(
                    time = value.uppercase(),
                    isSelected = selectedTimeSpan == value
                ) {
                    onTimeSpanSelected(this)
                }
            }

            with(ChartTimeSpan.All) {
                TimeSpanChip(
                    time = value.uppercase(),
                    isSelected = selectedTimeSpan == value
                ) {
                    onTimeSpanSelected(this)
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TimeSpanChip(
    time: String,
    isSelected: Boolean,
    onTimeSpanSelected: () -> Unit
) {

    Chip(
        onClick = {
            if(!isSelected){
                onTimeSpanSelected()
            }
        },
        modifier = Modifier,
        shape = RoundedCornerShape(16.dp),
        colors = ChipDefaults.chipColors(backgroundColor = if (isSelected) GreenBlue600 else MaterialTheme.colors.background)) {
        Text(
            text = time,
            color = if (isSelected) Color.White else MaterialTheme.colors.onBackground,
        )
    }
}

