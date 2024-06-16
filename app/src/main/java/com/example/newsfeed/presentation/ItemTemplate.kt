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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsfeed.R
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.ui.theme.Orange
import com.example.newsfeed.util.Dimens
import com.example.newsfeed.util.SourceButton

@Composable
fun ItemTemplate(
 item: ItemNewsUi,
 onItemClick: (ItemNewsUi) -> Unit,
 bookmarkClick: (ItemNewsUi) -> Unit
) {
 Column(
  modifier = Modifier
   .fillMaxWidth()
   .padding(5.dp)
   .background(Color.LightGray)
 ) {
  Row(
   modifier = Modifier.fillMaxWidth()
  ) {
   Box(
    modifier = Modifier
     .weight(1f)
   ) {
    TemplateRow(item, onItemClick)
   }
   BookmarkAndSource(item, bookmarkClick)
  }
 }
}

@Composable
private fun TemplateRow(
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
   .padding(top = 5.dp, start = 5.dp, bottom = 3.dp)
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
    .border(2.dp, Color.Black)
  ) {
   AsyncImage(
    model = imageUrl, contentDescription = "image",
    contentScale = ContentScale.Crop,
    modifier = Modifier
     .fillMaxHeight()
     .size(120.dp)
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