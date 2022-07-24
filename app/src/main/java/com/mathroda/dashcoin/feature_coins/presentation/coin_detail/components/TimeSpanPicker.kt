package com.mathroda.dashcoin.feature_coins.presentation.coin_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mathroda.dashcoin.feature_coins.domain.models.ChartTimeSpan


@Preview
@Composable
fun TimeSpanPickerPreview() {
    TimeSpanPicker(modifier = Modifier.fillMaxWidth())
}

@Composable
fun TimeSpanPicker(
    modifier: Modifier = Modifier,
    selectedTimeSpan: ChartTimeSpan = ChartTimeSpan.TimeSpan1Day,
    onTimeSpanSelected: (ChartTimeSpan) -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        TimeSpanChip(
            time = "24H",
            isSelected = selectedTimeSpan == ChartTimeSpan.TimeSpan1Day
        ) {
            onTimeSpanSelected(ChartTimeSpan.TimeSpan1Day)
        }

        TimeSpanChip(
            time = "1W",
            isSelected = selectedTimeSpan == ChartTimeSpan.TimeSpan1Week
        ) {
            onTimeSpanSelected(ChartTimeSpan.TimeSpan1Week)
        }

        TimeSpanChip(
            time = "1M",
            isSelected = selectedTimeSpan == ChartTimeSpan.TimeSpan1Month
        ) {
            onTimeSpanSelected(ChartTimeSpan.TimeSpan1Month)
        }

        TimeSpanChip(
            time = "3M",
            isSelected = selectedTimeSpan == ChartTimeSpan.TimeSpan3Months
        ) {
            onTimeSpanSelected(ChartTimeSpan.TimeSpan3Months)
        }

        TimeSpanChip(
            time = "6M",
            isSelected = selectedTimeSpan == ChartTimeSpan.TimeSpan6Months
        ) {
            onTimeSpanSelected(ChartTimeSpan.TimeSpan6Months)
        }

        TimeSpanChip(
            time = "1Y",
            isSelected = selectedTimeSpan == ChartTimeSpan.TimeSpan1Year
        ) {
            onTimeSpanSelected(ChartTimeSpan.TimeSpan1Year)
        }

        TimeSpanChip(
            time = "ALL",
            isSelected = selectedTimeSpan == ChartTimeSpan.TimeSpanAll
        ) {
            onTimeSpanSelected(ChartTimeSpan.TimeSpanAll)
        }
    }
}

@Composable
fun TimeSpanChip(
    time: String,
    isSelected: Boolean,
    onTimeSpanSelected: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = if (isSelected) MaterialTheme.colors.onBackground else MaterialTheme.colors.background,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                onTimeSpanSelected()
            }
    ) {
        Text(
            text = time,
            color = if (isSelected) MaterialTheme.colors.background else MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(8.dp)
                )
    }
}