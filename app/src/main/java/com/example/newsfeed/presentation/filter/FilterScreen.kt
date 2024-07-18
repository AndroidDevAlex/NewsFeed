package com.example.newsfeed.presentation.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsfeed.util.Dimens
import com.example.newsfeed.util.Headline
import com.example.newsfeed.util.NewsSource
import com.example.newsfeed.util.SourceButton
import com.example.newsfeed.util.TopBarCustom

@Composable
fun FilterScreen(navController: NavController) {

    val redditNewsViewModel: FilterViewModel = hiltViewModel()

    FilterScreenUi(
        clickItem = {})
}

@Composable
fun FilterScreenUi(
    clickItem: (String) -> Unit
) {
    val newsList = listOf(NewsSource.REDDIT, NewsSource.HABR)
    TopBarCustom {

        Text(
            text = Headline.ALL_GROUPS.title,
            color = Color.Black,
            fontSize = Dimens.HeadlineSize,
            modifier = Modifier.padding(Dimens.Padding),
            fontWeight = FontWeight.ExtraBold
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            Modifier
                .background(Color.White)
                .padding(Dimens.Padding)
        ) {
            itemsIndexed(newsList) { _, source ->
                SourceButton(
                    source = source,
                    onClick = {
                        clickItem(source.sourceName)
                    },
                    modifier = Modifier
                        .padding(start = Dimens.PaddingMod)
                        .padding(end = Dimens.PaddingMod)
                        .padding(top = Dimens.PaddingModifier)
                )
            }
        }
    }
}