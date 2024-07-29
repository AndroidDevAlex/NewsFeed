package com.example.newsfeed.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.newsfeed.ui.theme.HabrButton
import com.example.newsfeed.ui.theme.RedditButton

@Composable
fun SourceButton(source: NewsSource, modifier: Modifier = Modifier) {
    val backgroundColor = when (source) {
        NewsSource.HABR -> HabrButton
        NewsSource.REDDIT -> RedditButton
        else -> Color.Green
    }

    val contentColor = when (source) {
        NewsSource.HABR -> Color.Black
        NewsSource.REDDIT -> Color.White
        else -> Color.DarkGray
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(backgroundColor, shape = RoundedCornerShape(Dimens.CornerRadius))
            .padding(horizontal = Dimens.PaddingHorizontal, vertical = Dimens.PaddingVertical)
    ) {
        Text(
            text = source.sourceName,
            style = TextStyle(
                fontSize = Dimens.TextFontSizeSource,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        )
    }
}