package com.example.newsfeed.presentation.bookmark

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.newsfeed.navigation.Screen
import com.example.newsfeed.presentation.ItemTemplate
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.ui.theme.Orange
import com.example.newsfeed.util.Dimens
import com.example.newsfeed.util.Headline
import java.net.URLEncoder

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookmarkScreen(navController: NavController) {

    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()

    val state by bookmarkViewModel.state.collectAsState()

    BookmarkScreenUi(
        state = state,
        navigateToDetail = { news ->
            val encodedUrl = URLEncoder.encode(news.url, "UTF-8")
            navController.navigate(Screen.Details.route + "/$encodedUrl")
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
    navigateToDetail: (NewsUi) -> Unit,
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
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = Headline.SAVED.title,
                color = Color.Black,
                fontSize = Dimens.TopAppBarFontSize,
                fontWeight = FontWeight.Bold
            )
            LazyColumn {
                itemsIndexed(state.news) { _, item ->
                    ItemTemplate(
                        item = item,
                        onItemClick = {
                            navigateToDetail(it)
                        },
                        bookmarkClick = {
                            bookMarkClick(it)
                        },
                        isBookmarked = item.isBookmarked
                    )
                }
            }
        }
    }
}