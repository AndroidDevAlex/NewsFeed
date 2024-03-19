package com.example.newsfeed.util

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SourceButton(source: String, onClick: () -> Unit) {

    val buttonColors = when (source) {
        "habr" -> ButtonDefaults.buttonColors(
            backgroundColor = Color.Cyan,
            contentColor = Color.Magenta
        )

        "reddit" -> ButtonDefaults.buttonColors(
            backgroundColor = Color.Yellow,
            contentColor = Color.Black
        )

        else -> ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.DarkGray)
    }

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(Dimens.ClipPercent),
        colors = buttonColors
    ) {
        Text(
            text = source,
            style = TextStyle(
                fontSize = Dimens.TextFontSizeSource,
                fontWeight = FontWeight.Bold
            )
        )
    }
}