package com.dominic.coin_search.feature_coins.presentation.coin_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dominic.coin_search.feature_coins.domain.models.coin.CoinDetailModel
import com.dominic.coin_search.R

@Composable
fun TopBarCoinDetail(
    coinModel: CoinDetailModel,
    modifier: Modifier,
    backButtonOnClick: () -> Unit,
    isFavorite: Boolean,
    favoriteButtonOnClick: (Boolean) -> Unit

) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .requiredHeight(40.dp)
    ) {

        Box(modifier = Modifier
            .weight(2f),
            contentAlignment = Alignment.CenterStart
        ) {
            BackStackButton(
                modifier = Modifier
                    .padding(8.dp),
                onClick = backButtonOnClick
            )
        }
        Box(
            modifier = Modifier
                .weight(6f),
            contentAlignment = Alignment.Center){
            Row(horizontalArrangement = Arrangement.SpaceBetween) {

                Text(
                    text = coinModel.name,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.h6,
                    color = Color.White,
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(2f),
            contentAlignment = Alignment.CenterEnd){

                FavoriteButton(
                    modifier = Modifier.padding(8.dp),
                    isFavorite = isFavorite,
                    onClick = favoriteButtonOnClick
                )
        }

        }
}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    isFavorite: Boolean,
    onClick: (Boolean) -> Unit


) {
    IconToggleButton(
        checked = isFavorite ,
        onCheckedChange = onClick
    ) {
        Icon(
            tint = color,
            modifier = modifier.graphicsLayer {
                scaleX = 1f
                scaleY = 1.19f
            },
            painter =  painterResource (if (isFavorite) {
                R.drawable.ic_baseline_bookmark_filled
            } else {
                R.drawable.ic_baseline_bookmark_border_24
            }),
            contentDescription = "Save icon"
        )
    }

}

@Composable
fun BackStackButton(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    onClick: () -> Unit
) {

    IconButton(onClick = onClick) {

        Icon(
            tint = color,
            modifier = modifier.graphicsLayer {
                scaleX = 1.3f
                scaleY = 1.3f
            },
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null
        )

    }
}