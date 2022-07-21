package com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathroda.dashcoin.R
import com.mathroda.dashcoin.ui.theme.TextWhite

@Composable
fun TopBar(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier


    ) {

        Row(modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 1.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.Start)){
            Icon(
                painter = painterResource(id = R.drawable.ic_app_icon),
                contentDescription = "App Icon",
                tint = Color.Unspecified,
                modifier = Modifier.size(30.dp)
            )
            Text(fontFamily = FontFamily.SansSerif,
                color = Color.White,
                fontSize = 16.sp,
                text = buildAnnotatedString{
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Light)){
                        append("Coin")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)){
                        append("Search")
                    }

                }
            )
        }


        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 1.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(11.dp, alignment = Alignment.End)) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_sun),
                    contentDescription = "Toggle theme icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.dp).clickable {
                        /*TODO*/
                    }
                )

            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "USD",
                    style = MaterialTheme.typography.button,
                    color = Color.White
                )
            }

                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.dp).clickable {
                        /*TODO*/
                    }
                )

        }
    }



    
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar(modifier = Modifier.wrapContentSize())
}