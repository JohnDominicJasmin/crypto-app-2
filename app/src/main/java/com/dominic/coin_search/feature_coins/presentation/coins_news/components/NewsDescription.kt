package com.dominic.coin_search.feature_coins.presentation.coins_news.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dominic.coin_search.R
import com.dominic.coin_search.core.util.Formatters.toTimeAgo
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import com.dominic.coin_search.ui.theme.GreenBlue600


@Composable
fun NewsDescription(
    modifier: Modifier = Modifier,
    isItemSaved: Boolean,
    newsModel: NewsModel,
    onSaveIconClick: () -> Unit) {
    Row(modifier = modifier.fillMaxWidth()) {

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(0.8f)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_access_time_24),
                tint = Color.White,
                contentDescription = "Time Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${newsModel.feedDate.toTimeAgo()} | ${newsModel.source}",
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 5.dp),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )
        }




            Icon(
                painter = painterResource(id = if (isItemSaved) R.drawable.ic_baseline_bookmark_filled else R.drawable.ic_baseline_bookmark_border_24),
                contentDescription = "Save Icon",
                modifier = Modifier
                    .size(22.dp)
                    .weight(0.1f)
                    .clickable {
                        onSaveIconClick()
                    },
                tint = if (isItemSaved) GreenBlue600 else Color.White
            )
    }
}