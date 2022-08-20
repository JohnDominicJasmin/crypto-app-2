package com.dominic.coin_search.feature_coins.presentation.coins_news

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dominic.coin_search.core.util.ConnectionStatus
import com.dominic.coin_search.feature_coins.presentation.coins_news.components.NewsItemLarge
import com.dominic.coin_search.feature_coins.presentation.coins_news.components.NewsItemSmall
import com.dominic.coin_search.feature_coins.presentation.coins_news.components.NewsTitleSection
import com.dominic.coin_search.feature_coins.presentation.coins_news.components.PagerIndicator
import com.dominic.coin_search.feature_coins.presentation.coins_screen.components.TopBar
import com.dominic.coin_search.feature_no_internet.presentation.NoInternetScreen
import com.dominic.coin_search.ui.theme.DarkGray
import com.dominic.coin_search.ui.theme.Green800
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun NewsScreen(
    innerPaddingValues: PaddingValues,
    newsViewModel: NewsViewModel = hiltViewModel(),
    navController: NavController?
) {
    val state by newsViewModel.state.collectAsState()
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    val pagerState = rememberPagerState()

    Scaffold(topBar = {
        TopBar(
            allowSearchField = false,
            currencyValue = null,
            modifier = Modifier
                .height(55.dp)
                .padding(bottom = 5.dp, top = 14.dp, start = 15.dp, end = 5.dp)
                .fillMaxWidth(),
           )
    }) {

        Box(
            modifier = Modifier
                .padding(innerPaddingValues)
                .background(DarkGray)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {


                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = state.isRefreshing),
                    onRefresh = { newsViewModel.onEvent(event = NewsEvent.RefreshNews) }) {

                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 10.dp)) {

                        item {

                            if (state.trendingNews.isNotEmpty()) {
                                NewsTitleSection(title = "Today's News", modifier = Modifier.padding(bottom = 10.dp, top = 15.dp))
                            }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    HorizontalPager(
                                        count = state.trendingNews.take(7).size,
                                        state = pagerState) { pageIndex ->

                                        val (news, isSaved) = state.trendingNews.take(7)[pageIndex]
                                        NewsItemLarge(
                                            isSavedNews = isSaved,
                                            newsModel = news,
                                            onItemClick = { /*TODO: open browser*/ },
                                            onSaveClick = {/*TODO: save item from db*/ }
                                        )
                                    }
                                    PagerIndicator(
                                        pagerState = pagerState,
                                        modifier = Modifier.padding(vertical = 10.dp)
                                    )

                                }

                        }




                        item {
                            if (state.latestNews.isNotEmpty()) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()) {
                                    NewsTitleSection(title = "Latest News",)
                                    Spacer(modifier = Modifier.weight(0.6f))
                                    IconButton(onClick = {/*TODO*/}){
                                        Icon(
                                            imageVector = Icons.Filled.ArrowForward,
                                            tint = Color.White,
                                            modifier = Modifier.size(24.dp),
                                            contentDescription = "See all news"

                                        )
                                    }
                                }
                            }
                                LazyRow(modifier = Modifier.padding(bottom = 12.dp)) {

                                    items(items = state.latestNews.take(12), key = {it.first.id}) { newsModel ->
                                        NewsItemSmall(
                                            modifier = Modifier.widthIn(290.dp),
                                            newsModel = newsModel.first,
                                            onItemClick = { /*TODO: open browser*/ },
                                            onSaveClick = {/*TODO: delete item from db*/ },
                                            isSavedNews = newsModel.second
                                        )
                                  }
                                }
                        }


                        if(merge(state.bearishNews, state.bullishNews).isNotEmpty()) {
                            item {
                                NewsTitleSection(title = "For you", modifier = Modifier.padding(bottom = 10.dp, top = 15.dp))
                            }

                        }
                        items(items = merge(state.bearishNews, state.bullishNews), key = {it.first.id}) { newsModel ->
                            NewsItemSmall(
                                modifier = Modifier.padding(vertical = 2.dp),
                                newsModel = newsModel.first,
                                onItemClick = { /*TODO: open browser*/ },
                                onSaveClick = {/*TODO: delete item from db*/ },
                                isSavedNews = newsModel.second
                            )
                        }

                        }

                    }
                }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = Green800
                )
            }

            if (!state.hasInternet) {
                NoInternetScreen(onTryButtonClick = {
                    if (ConnectionStatus.hasInternetConnection(context)) {
                        newsViewModel.onEvent(event = NewsEvent.CloseNoInternetDisplay)
                        newsViewModel.onEvent(event = NewsEvent.RefreshNews)
                    }
                })
            }

            if (state.errorMessage.isNotEmpty()) {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

fun <T> merge(first: List<T>, second: List<T>): List<T> {
    return first.plus(second)
}