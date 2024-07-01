package com.example.newsfeed.util

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SourceButton(source: NewsSource, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val buttonColors = when (source) {
        NewsSource.HABR -> ButtonDefaults.buttonColors(
            backgroundColor = Color.Black,
            contentColor = Color.Magenta
        )

        NewsSource.REDDIT -> ButtonDefaults.buttonColors(
            backgroundColor = Color.Red,
            contentColor = Color.White
        )

        else -> ButtonDefaults.buttonColors(
            backgroundColor = Color.Green,
            contentColor = Color.DarkGray
        )
    }

    Button(
        onClick = onClick,
        colors = buttonColors,
        modifier = modifier
    ) {
        Text(
            text = source.sourceName,
            style = TextStyle(
                fontSize = Dimens.TextFontSizeSource,
                fontWeight = FontWeight.Bold
            )
        )
    }
}