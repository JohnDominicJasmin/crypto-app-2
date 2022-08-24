package com.dominic.coin_search.feature_coins.presentation.coins_news.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import com.dominic.coin_search.ui.theme.Black920
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.dominic.coin_search.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsItemLarge(
    modifier: Modifier = Modifier,
    isSavedNews: Boolean,
    newsModel: NewsModel,
    onItemClick: () -> Unit,
    onSaveClick: (isAlreadySaved: Boolean) -> Unit) {

    val (isSavedNewsItem, onIconSaveClick) = rememberSaveable {
        mutableStateOf(isSavedNews)
    }



        Card(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Black920,
            modifier = modifier
                .padding(vertical = 5.dp, horizontal = 12.dp)
                .heightIn(max = 180.dp)
                .fillMaxWidth(), onClick = onItemClick) {

            Box {
                AsyncImage(
                    model = newsModel.imgURL,
                    contentDescription = "News Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alpha = 0.4f,
                    error = painterResource(id = R.drawable.ic_baseline_image_24),

                    )

                Column(
                    modifier = Modifier.align(Alignment.BottomStart)) {

                    Text(
                        color = Color.White,
                        text = newsModel.title,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.padding(start = 14.dp, end = 50.dp)
                    )

                    NewsDescription(
                        isItemSaved = isSavedNewsItem,
                        modifier = Modifier
                            .padding(horizontal = 14.dp)
                            .padding(top = 5.dp, bottom = 12.dp),
                        newsModel = newsModel,
                        onSaveIconClick = {
                            onIconSaveClick(!isSavedNewsItem)
                            onSaveClick(isSavedNewsItem)
                    })
                }
            }
        }
    }

@Preview
@Composable
fun NewsItemLargePreview() {
    NewsItemLarge(modifier = Modifier, newsModel = NewsModel(
        feedDate = (1660582800000),
        imgURL = "https://ambcrypto.com/wp-content/uploads/2022/08/quantitatives-aL7xbjJDZMc-unsplash-1.jpg",
        source = "Ambcrypto",
        title = "Bonjour Shiba Inu [SHIB] holders, you should be on cloud nine because...",
    ), onSaveClick = {}, onItemClick = {}, isSavedNews = false)
}