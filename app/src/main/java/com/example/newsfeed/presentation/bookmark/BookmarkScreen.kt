package com.example.newsfeed.presentation.bookmark

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsfeed.R
import com.example.newsfeed.presentation.LoadingStateView
import com.example.newsfeed.presentation.NewsItem
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.util.TopBarCustom
import com.example.newsfeed.util.Dimens
import com.example.newsfeed.util.Headline
import com.example.newsfeed.util.navigateAndCheckIsConnected

@Composable
fun BookmarkScreen(navController: NavController) {

    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()

    val savedPagingNews = bookmarkViewModel.bookmarkedPagingDataFlow.collectAsLazyPagingItems()

    val isConnected by bookmarkViewModel.isConnected.collectAsState()
    val context = LocalContext.current

    BookmarkScreenUi(
        bookmarkedNews = savedPagingNews,
        navigateToDetail = { news ->
            navigateAndCheckIsConnected(navController, news, isConnected, context)
        },
        bookMarkClick = { news ->
            bookmarkViewModel.onBookmarkClicked(news)
        }
    )
}

@Composable
private fun BookmarkScreenUi(
    bookmarkedNews: LazyPagingItems<ItemNewsUi>,
    navigateToDetail: (ItemNewsUi) -> Unit,
    bookMarkClick: (ItemNewsUi) -> Unit
) {
    TopBarCustom {
        when (bookmarkedNews.itemCount) {
            0 -> {
                Text(
                    text = stringResource(R.string.no_saved_news),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = Dimens.FontSize,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = Dimens.PaddingTop)
                )
            }

            else -> {
                Text(
                    text = Headline.SAVED.title,
                    color = Color.Black,
                    fontSize = Dimens.HeadlineSize,
                    fontWeight = FontWeight.Bold
                )

                LazyColumn {
                    items(bookmarkedNews.itemCount) { index ->
                        bookmarkedNews[index]?.let { news ->
                            NewsItem(
                                item = news,
                                onItemClick = { navigateToDetail(it) },
                                bookmarkClick = { bookMarkClick(it) }
                            )
                        }
                    }
                }
                LoadingStateView(bookmarkedNews.loadState)
            }
        }
    }
}