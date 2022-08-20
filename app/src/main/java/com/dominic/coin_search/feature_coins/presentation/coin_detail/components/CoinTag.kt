package com.dominic.coin_search.feature_coins.presentation.coin_detail.components


import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dominic.coin_search.core.util.Constants.GOOGLE_SEARCH_QUERY
import com.dominic.coin_search.ui.theme.GreenBlue600


@ExperimentalMaterialApi
@Composable
fun CoinTag(tag: String) {
    val context = LocalContext.current


    OutlinedButton(
        onClick = { openBrowser(context = context, searchItem = GOOGLE_SEARCH_QUERY + tag) },
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 1.7.dp, GreenBlue600),

        ) {

        Text(
            modifier = Modifier.padding(vertical = 3.5.dp, horizontal = 3.dp),
            text = tag,
            color = Color.White,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Medium
        )
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun CoinTagPreview() {
    CoinTag(tag = "Bitcoin")
}


fun openBrowser(context: Context, searchItem: String) {
    Intent(
        Intent.ACTION_VIEW,
        Uri.parse( searchItem))
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        .addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        .also(context::startActivity)
}