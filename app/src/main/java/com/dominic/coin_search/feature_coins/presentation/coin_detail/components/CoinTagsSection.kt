package com.dominic.coin_search.feature_coins.presentation.coin_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CoinTagsSection(modifier: Modifier, tags: List<String>) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Tags",
            style = MaterialTheme.typography.h5,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp)


        FlowRow(
            mainAxisSpacing = 5.dp,
            crossAxisSpacing = 1.dp,
            modifier = Modifier.fillMaxWidth()) {
            tags.forEach { CoinTag(it) }
        }
    }
}

@Preview
@Composable
fun CoinTagsSectionPreview() {

    CoinTagsSection(
        tags = listOf("tag1", "tag2", "tag3", "tag4", "tag5"),
        modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp))

}