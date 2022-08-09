package com.dominic.coin_search.feature_coins.presentation.coin_detail.components


import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dominic.coin_search.core.util.Constants.GOOGLE_SEARCH_QUERY


@ExperimentalMaterialApi
@Composable
fun CoinTag(tag: String) {

    val context = LocalContext.current

    Surface(modifier = Modifier
        .border(
            width = 2.dp,
            color = Color.Green,
            shape = RoundedCornerShape(15.dp))
        .padding(all = 13.dp),
        onClick = {
            openBrowser(context = context, searchItem = tag)
        }) {

        Text(
            text = tag,
            color = Color.White,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold
        )
    }

}


fun openBrowser(context: Context, searchItem: String) {
    Intent(
        Intent.ACTION_VIEW,
        Uri.parse(GOOGLE_SEARCH_QUERY + searchItem)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        .also(context::startActivity)
}