package com.dominic.coin_search.feature_coins.presentation.coin_detail.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dominic.coin_search.ui.theme.Black920
import com.dominic.coin_search.ui.theme.Twitter

@Composable
fun CoinLinkButtons(onOpenTwitterUrl: () -> Unit, onOpenWebsiteUrl: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp, alignment = Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp, start = 25.dp, end = 25.dp )) {
        LinkButton(
            title = "Twitter",
            modifier = Modifier
                .clip(RoundedCornerShape(35.dp))
                .height(45.dp)
                .background(Twitter)
                .weight(1f)
                .clickable {
                   onOpenTwitterUrl()
                }
        )

        LinkButton(
            title = "Website",
            modifier = Modifier
                .clip(RoundedCornerShape(35.dp))
                .height(45.dp)
                .background(Black920)
                .weight(1f)
                .clickable {
                   onOpenWebsiteUrl()
                }
        )
    }
}

