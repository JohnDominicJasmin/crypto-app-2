package com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dominic.coin_search.R
import com.dominic.coin_search.ui.theme.DarkGray
import com.dominic.coin_search.ui.theme.GreenBlue600

@Composable
fun FavoriteAddButton(modifier: Modifier = Modifier,onClick: () -> Unit) {
    val stroke = Stroke(
        width = 7f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f,12f), 10f)
    )
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.padding(bottom = 20.dp, top = 12.dp).heightIn(max = 52.dp),
        contentPadding = PaddingValues(all = 0.dp),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
        shape = RoundedCornerShape(16.dp),
) {
        Box {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRoundRect(color = GreenBlue600.copy(alpha = 0.5f), style = stroke, cornerRadius = CornerRadius(x = 50f, y = 50f))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.align(Alignment.Center)) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_add_circle_24),
                    tint = GreenBlue600,
                    contentDescription = "Add Favorite Icon",
                modifier = Modifier.size(17.dp))
                Text(
                    modifier = Modifier.padding(all = 4.dp),
                    text = "Add More",
                    textAlign = TextAlign.Center,
                    color = GreenBlue600,
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.5.sp)
            }

        }
    }
}

@Preview(widthDp = 200, heightDp = 100)
@Composable
fun FavoriteAddButtonPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray).padding(top = 20.dp, start = 20.dp, end = 20.dp)){
        FavoriteAddButton {}
    }
}