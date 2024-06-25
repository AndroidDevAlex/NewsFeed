package com.example.newsfeed.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.newsfeed.R
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.ui.theme.Orange
import com.example.newsfeed.util.Dimens
import com.example.newsfeed.util.SourceButton

@Composable
fun NewsItem(
    item: ItemNewsUi,
    onItemClick: (ItemNewsUi) -> Unit,
    bookmarkClick: (ItemNewsUi) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                showDialog = true
            }) {

        if (showDialog) {
            OpenNewsDetailsDialog(
                onDismiss = { showDialog = false },
                onConfirm = {
                    showDialog = false
                }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.PaddingTemplate)
            .background(Color.LightGray)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f)) {
                ImageAndContent(item, onItemClick)
            }
            BookmarkAndSource(item, bookmarkClick)
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
        .clickable() {
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
                start = Dimens.PaddingTemplate,
                bottom = Dimens.PaddingBottom
            )
    ) {
        Text(
            text = item.publishedAt,
            style = TextStyle(
                fontSize = Dimens.TextFontSize,
                color = Color.Gray,
                fontWeight = FontWeight.Normal
            )
        )
        Text(
            text = item.title,
            style = TextStyle(
                fontSize = Dimens.TextFontSizeTitle,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
        )
    }
}

@Composable
private fun NewsImage(item: ItemNewsUi) {
    item.image?.let { imageUrl ->
        Box(
            modifier = Modifier
                .border(Dimens.Border, Color.Black)
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
    onBookmarkClick: (ItemNewsUi) -> Unit
) {

    val bookmarkIcon: Painter = painterResource(id = R.drawable.bookmark)

    val tint = if (item.isBookmarked) Orange else Color.Gray

    Column(horizontalAlignment = Alignment.End) {

        SourceButton(
            source = item.source,
            onClick = {}
        )

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