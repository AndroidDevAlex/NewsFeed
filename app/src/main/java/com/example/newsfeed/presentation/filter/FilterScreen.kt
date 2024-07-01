package com.example.newsfeed.presentation.filter

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsfeed.R
import com.example.newsfeed.ui.theme.Blue
import com.example.newsfeed.ui.theme.DarkGray
import com.example.newsfeed.util.Dimens
import com.example.newsfeed.util.Headline
import com.example.newsfeed.util.NewsSource
import com.example.newsfeed.util.SourceButton

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun FilterScreen(navController: NavController) {

    val redditNewsViewModel: FilterViewModel = hiltViewModel()

    FilterScreenUi(
        clickItem = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilterScreenUi(
    clickItem: (String) -> Unit
) {
    val newsList = listOf(NewsSource.REDDIT, NewsSource.HABR)

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = Blue,
                    fontSize = Dimens.TopAppBarFontSize,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
            }, colors = topAppBarColors(
                DarkGray
            )
        )
    }) {

        Column(
            Modifier
                .padding(Dimens.BetweenItems),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(Dimens.SpacerHeight))
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
                        modifier = Modifier.padding(Dimens.BetweenButton)
                    )
                }
            }
        }
    }
}