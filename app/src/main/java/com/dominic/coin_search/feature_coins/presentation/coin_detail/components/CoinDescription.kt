package com.dominic.coin_search.feature_coins.presentation.coin_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dominic.coin_search.ui.theme.Black450
import com.dominic.coin_search.ui.theme.DarkGray

@Composable
fun CoinDescription(modifier: Modifier, description: String) {
    Text(
        text = description.ifEmpty { "Description is not available." },
        color = if(description.isEmpty()) Color.Gray else Black450,
        style = MaterialTheme.typography.body2,
        lineHeight = 21.sp,
        modifier = modifier,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Clip,

    )

}

@Preview
@Composable
fun CoinDescriptionPreview() {
    Box(modifier = Modifier.background(DarkGray).fillMaxSize()){

    CoinDescription(
        modifier = Modifier
            .wrapContentHeight()
            .padding(vertical = 10.dp, horizontal = 8.dp),
        description = "Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia, molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum\n" +
                      "numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium optio, eaque rerum! Provident similique accusantium nemo autem.")

}

}