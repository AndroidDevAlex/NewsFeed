package com.example.newsfeed.presentation.bookmark

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsfeed.R
import com.example.newsfeed.navigation.Routes
import com.example.newsfeed.presentation.ItemTemplate
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.ui.theme.Orange
import com.example.newsfeed.util.Dimens
import com.example.newsfeed.util.Headline

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookmarkScreen(navController: NavController) {

    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()

    val state by bookmarkViewModel.state.collectAsState()

    BookmarkScreenUi(
        state,
        navigateToDetail = { id, url ->
            // переход на DETAILS
            navController.navigate(Routes.DETAILS.name)
        },
        bookMarkClick = { news ->
            bookmarkViewModel.onBookmarkClicked(news)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
private fun BookmarkScreenUi(
    state: BookmarkState,
    navigateToDetail: (Int, String) -> Unit,
    bookMarkClick: (NewsUi) -> Unit
) {

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = Color.White,
                    fontSize = Dimens.TopAppBarFontSize,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
            }, colors = TopAppBarDefaults.smallTopAppBarColors(Orange)
        )
    }) {
        Column(
            Modifier
                .fillMaxHeight()
                .padding(60.dp), horizontalAlignment = Alignment.Start) {
            Text(
                text = Headline.SAVED.title,
                color = Color.DarkGray,
                fontSize = Dimens.TopAppBarFontSize,
                modifier = Modifier.padding(Dimens.Padding)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Dimens.PaddingHorizontal)
            ) {
                itemsIndexed(state.news) { _, item ->
                    ItemTemplate(
                        item = item,
                        onItemClick = {
                            item.source?.let { url -> navigateToDetail(item.id, url) }
                        },
                        onBookmarkClick = {
                            bookMarkClick(item)
                        })
                }
            }
        }
    }
}