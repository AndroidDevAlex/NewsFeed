package com.example.newsfeed.presentation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.newsfeed.ui.theme.HabrButton
import com.example.newsfeed.ui.theme.RedditButton
import com.example.newsfeed.util.Dimens
import com.example.newsfeed.util.NewsSource

@Composable
fun SourceButton(
    source: NewsSource,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isBookmarkScreen: Boolean,
    isSelected: Boolean = false
) {
    Button(
        shape = RoundedCornerShape(Dimens.CornerRadius),
        onClick = onClick,
        colors = sourceButtonColors(isSelected = isSelected, source = source, isBookmarkScreen),
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

@Composable
fun sourceButtonColors(
    isSelected: Boolean,
    source: NewsSource,
    isBookmarkScreen: Boolean
): ButtonColors {
    return if (isBookmarkScreen) {
        getButtonColorsBySource(source = source)
    } else {
        if (isSelected) {
            getButtonColorsBySource(source = source)
        } else {
            ButtonDefaults.buttonColors(
                backgroundColor = Color.LightGray,
                contentColor = Color.White
            )
        }
    }
}

@Composable
fun getButtonColorsBySource(source: NewsSource): ButtonColors {
    return when (source) {
        NewsSource.HABR -> ButtonDefaults.buttonColors(
            backgroundColor = HabrButton,
            contentColor = Color.Black
        )

        NewsSource.REDDIT -> ButtonDefaults.buttonColors(
            backgroundColor = RedditButton,
            contentColor = Color.White
        )

        else -> ButtonDefaults.buttonColors(
            backgroundColor = Color.Green,
            contentColor = Color.DarkGray
        )
    }
}