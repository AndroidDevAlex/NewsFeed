package com.example.newsfeed.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.newsfeed.R
import com.example.newsfeed.util.SourceButton
import com.example.newsfeed.ui.theme.GrayTemplate
import com.example.newsfeed.ui.theme.Orange
import com.example.newsfeed.util.Dimens

@Composable
fun ItemTemplate(
 item: NewsUi,
 onItemClick: () -> Unit,
 onBookmarkClick: () -> Unit
) {

 var isHighlighted by remember { mutableStateOf(false) }

 Card(
  modifier = Modifier
   .fillMaxWidth()
   .height(Dimens.CardHeight)
   .padding(Dimens.CardPadding)
   .background(GrayTemplate)
   .clickable { onItemClick() }
 ) {
  Row(verticalAlignment = Alignment.CenterVertically) {

   val bookmarkIcon: Painter =
    if (isHighlighted) painterResource(id = R.drawable.bookmark_orange)
    else painterResource(id = R.drawable.bookmark)

   Image(
    painter = painterResource(id = item.image.toInt()),
  //  painter = rememberPicassoPainter(item.image),
    contentDescription = "image",
    modifier = Modifier
     .padding(Dimens.ImagePadding)
     .size(Dimens.ImageSize)
     .clip(RoundedCornerShape(percent = Dimens.ClipPercent))
     .heightIn(120.dp)
   )
   Column(
    modifier = Modifier
     .weight(1f),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.Start
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
     modifier = Modifier
      .padding(vertical = Dimens.Padding)
    )
    Column(
     modifier = Modifier.fillMaxWidth(),
     verticalArrangement = Arrangement.SpaceBetween,
     horizontalAlignment = Alignment.End
    ) {

     item.source?.let {
      SourceButton(
       source = it,
       onClick = onItemClick
      )
     }

     Icon(
      painter = bookmarkIcon,
      contentDescription = "Bookmark",
      tint = if (isHighlighted) Orange else Color.Gray,
      modifier = Modifier
       .clickable {
        isHighlighted = !isHighlighted
        onBookmarkClick()
       }
       .padding(end = Dimens.Padding)
     )
    }
   }
  }
 }
}