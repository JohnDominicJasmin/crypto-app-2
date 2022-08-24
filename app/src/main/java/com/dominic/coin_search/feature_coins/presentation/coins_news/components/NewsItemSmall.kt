package com.dominic.coin_search.feature_coins.presentation.coins_news.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.dominic.coin_search.R
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import com.dominic.coin_search.ui.theme.Black920

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsItemSmall(
    modifier: Modifier = Modifier,
    newsModel: NewsModel,
    isSavedNews: Boolean,
    onItemClick: () -> Unit,
    onSaveClick: (isAlreadySaved: Boolean) -> Unit) {

    val (isSavedNewsItem, onIconSaveClick) = rememberSaveable {
        mutableStateOf(isSavedNews)
    }

        Card(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Black920,
            modifier = modifier
                .padding(vertical = 5.dp)
                .padding(start = 12.dp, end = 2.dp)
                .heightIn(max = 120.dp)
                .fillMaxWidth(), onClick = onItemClick) {


            Row {
                AsyncImage(
                    model = newsModel.imgURL,
                    contentDescription = "News Headline Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.3f),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.ic_baseline_image_24))



                Column(
                    modifier = Modifier
                        .weight(0.7f)
                        .fillMaxHeight()
                        .padding(start = 10.dp, end = 10.dp, top = 6.dp, bottom = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)) {

                    Text(
                        text = newsModel.title,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                        style = MaterialTheme.typography.subtitle2,
                        maxLines = 2,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 18.sp
                    )


                    Spacer(modifier = Modifier.weight(0.7f))
                    NewsDescription(
                        isItemSaved = isSavedNewsItem,
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
fun NewsItemPreview() {
    NewsItemSmall(
        newsModel = NewsModel(
            feedDate = (1660582800000),
            imgURL = "https://ambcrypto.com/wp-content/uploads/2022/08/quantitatives-aL7xbjJDZMc-unsplash-1.jpg",
            source = "Ambcrypto",
            title = "Bonjour Shiba Inu [SHIB] holders, you should be on cloud nine because...",
        ),
        modifier = Modifier,
        onItemClick = {},
        onSaveClick = {}, isSavedNews = false)
}

