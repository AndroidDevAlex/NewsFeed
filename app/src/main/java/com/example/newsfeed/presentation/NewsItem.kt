package com.example.newsfeed.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.example.newsfeed.R
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.util.Dimens

@Composable
fun NewsItem(
    item: ItemNewsUi,
    onItemClick: (ItemNewsUi) -> Unit,
    bookmarkClick: (ItemNewsUi) -> Unit,
    selectedSources: List<String>?,
    isBookmarkScreen: Boolean = false
) {
    val isSelectedSource = if (isBookmarkScreen) {
        false
    } else {
        selectedSources?.contains(item.source) == true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.PaddingBetween)
            .background(Color.LightGray)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f)) {
                ImageAndContent(item, onItemClick)
            }
            BookmarkAndSource(item, bookmarkClick, isSelectedSource, isBookmarkScreen)
        }
    }
}

@Composable
private fun ImageAndContent(
    newsUi: ItemNewsUi,
    onItemClick: (ItemNewsUi) -> Unit
) {
    Row(modifier =
    Modifier
        .clickable {
            onItemClick(newsUi)
        }
    ) {
        NewsImage(newsUi)
        NewsContent(newsUi)
    }
}

@Composable
private fun NewsContent(item: ItemNewsUi) {
    Column(
        modifier =
        Modifier
            .padding(
                top = Dimens.PaddingTemplate,
                start = Dimens.PaddingTemplate
            )
    ) {
        Text(
            text = item.publishedAt,
            style = TextStyle(
                fontSize = Dimens.TextFontSize,
                color = Color.DarkGray,
                fontWeight = FontWeight.Normal
            )
        )
        Spacer(modifier = Modifier.height(Dimens.SpacerBetweenContent))
        Text(
            text = item.title,
            style = TextStyle(
                fontSize = Dimens.TextFontSizeTitle,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun NewsImage(item: ItemNewsUi) {
    item.image?.let { imageUrl ->
        Box(
            modifier = Modifier
                .border(Dimens.Border, Color.Black)
                .fillMaxHeight()
        ) {
            AsyncImage(
                model = imageUrl, contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .size(Dimens.ImageSize)
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun BookmarkAndSource(
    item: ItemNewsUi,
    onBookmarkClick: (ItemNewsUi) -> Unit,
    isSelectedSource: Boolean,
    isBookmarkScreen: Boolean
) {

    val bookmarkIcon: Painter = painterResource(id = R.drawable.bookmark)

    val tint = if (item.isBookmarked) Color.DarkGray else Color.White

    Column(horizontalAlignment = Alignment.End) {

        SourceButton(
            source = item.source,
            onClick = {},
            modifier = Modifier.padding(top = Dimens.PaddingTemplate),
            isSelected = isSelectedSource,
            isBookmarkScreen = isBookmarkScreen
        )

        Spacer(modifier = Modifier.height(Dimens.Height))
        Icon(
            painter = bookmarkIcon,
            contentDescription = "Bookmark",
            tint = tint,
            modifier = Modifier
                .clickable {
                    onBookmarkClick(item)
                }
        )
    }
}