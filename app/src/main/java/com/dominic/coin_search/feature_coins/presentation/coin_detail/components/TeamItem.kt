package com.dominic.coin_search.feature_coins.presentation.coin_detail.components


import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.dominic.coin_search.feature_coins.data.dto.Team

@Composable
fun TeamListItem(
    teamMember: Team,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center) {

        Text(
            text = teamMember.name,
            style = MaterialTheme.typography.h6,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = teamMember.position,
            style = MaterialTheme.typography.caption,
            fontStyle = FontStyle.Italic
        )


    }
}