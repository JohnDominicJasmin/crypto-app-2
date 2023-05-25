@file:OptIn(ExperimentalPagerApi::class)

package com.dominic.coin_search.feature_intro_slider_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dominic.coin_search.ui.theme.DashCoinTheme
import com.dominic.coin_search.feature_intro_slider_screen.presentation.components.IntroSliderButtonSection
import com.dominic.coin_search.feature_intro_slider_screen.presentation.components.IntroSliderItem
import com.dominic.coin_search.navigation.Screens
import com.dominic.coin_search.navigation.navigateScreenInclusively
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun IntroSliderScreen(
    introSliderViewModel: IntroSliderViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    navController: NavController) {


    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val isOnLastPage by remember {
        derivedStateOf { pagerState.currentPage == 3 }
    }

    val onClickSkipButton = remember {
        {
            introSliderViewModel.onEvent(event = IntroSliderEvent.UserCompletedWalkThrough)
            navController.navigateScreenInclusively(
                Screens.SignInScreen.route,
                Screens.IntroSliderScreen.route)
        }
    }
    val onClickNextButton = remember(pagerState.currentPage) {
        {

            if(isOnLastPage){
                introSliderViewModel.onEvent(event = IntroSliderEvent.UserCompletedWalkThrough)
                 navController.navigateScreenInclusively(
                     Screens.SignInScreen.route,
                     Screens.IntroSliderScreen.route)
            }else{
                scope.launch {
                    pagerState.animateScrollToPage(
                        page = pagerState.currentPage + 1
                    )
                }
            }

            Unit
        }
    }


    IntroSliderContent(
        modifier = Modifier

            .padding(paddingValues),
        pagerState = pagerState,
        onClickSkipButton = onClickSkipButton,
        onClickNextButton = onClickNextButton,
        isOnLastPage = isOnLastPage
        )

}


@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun IntroSliderPreview() {
    DashCoinTheme(darkTheme = true) {
        IntroSliderContent(
            modifier = Modifier
                .background(MaterialTheme.colors.background),
            pagerState = rememberPagerState(),
            onClickSkipButton = {},
            onClickNextButton = {},
            isOnLastPage = false
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun IntroSliderContent(
    modifier: Modifier,
    pagerState: PagerState,
    isOnLastPage: Boolean,
    onClickSkipButton: () -> Unit,
    onClickNextButton: () -> Unit) {


    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colors.background) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {


            IntroSliderItem(
                pagerState = pagerState,
                modifier = Modifier
                    .weight(0.7f)
            )



            IntroSliderButtonSection(
                modifier = Modifier
                    .weight(0.3f),
                text = if (isOnLastPage) "Let's get Started!" else "Next",
                onClickSkipButton = onClickSkipButton,
                onClickNextButton = onClickNextButton)
        }
    }
}





