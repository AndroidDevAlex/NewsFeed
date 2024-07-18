package com.example.newsfeed.presentation.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsfeed.presentation.LoadingStateView
import com.example.newsfeed.presentation.NewsItem
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.util.TopBarCustom
import com.example.newsfeed.util.Dimens
import com.example.newsfeed.util.Headline
import com.example.newsfeed.util.navigateAndCheckIsConnected
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun NewsScreen(
    navController: NavController
) {
    val newsViewModel = hiltViewModel<NewsViewModel>()
    val newsPaging = newsViewModel.allNews.collectAsLazyPagingItems()

    val isRefresh by newsViewModel.isRefreshing.collectAsState()

    val isConnected by newsViewModel.isConnected.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefresh)

    val context = LocalContext.current

    NewsScreenUi(
        onRefresh = {
            newsViewModel.refreshScreen()
        },
        bookMarkClick = {
            newsViewModel.pressBookmark(it)
        },
        navigateToDetail = { news ->
            navigateAndCheckIsConnected(navController, news, isConnected, context)
        },
        swipeRefreshState = swipeRefreshState,
        newsPagingData = newsPaging
    )
}

@Composable
private fun NewsScreenUi(
    onRefresh: () -> Unit,
    bookMarkClick: (ItemNewsUi) -> Unit,
    navigateToDetail: (ItemNewsUi) -> Unit,
    swipeRefreshState: SwipeRefreshState,
    newsPagingData: LazyPagingItems<ItemNewsUi>
) {

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { onRefresh() }
    ) {
        TopBarCustom {
            Text(
                text = Headline.ALL_FEEDS.title,
                color = Color.Black,
                fontSize = Dimens.HeadlineSize,
                fontWeight = FontWeight.ExtraBold
            )

            LazyColumn {
                items(newsPagingData.itemCount) { index ->
                    newsPagingData[index]?.let { news ->
                        NewsItem(
                            item = news,
                            onItemClick = { navigateToDetail(it) },
                            bookmarkClick = { bookMarkClick(it) }
                        )
                    }
                }
            }
            LoadingStateView(newsPagingData.loadState)
        }
    }
}