package com.example.newsfeed.presentation.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsfeed.R
import com.example.newsfeed.internetConection.NetworkViewModel
import com.example.newsfeed.navigation.Screen
import com.example.newsfeed.presentation.NewsItem
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.util.TopBarCustom
import com.example.newsfeed.util.Dimens
import com.example.newsfeed.util.Headline
import com.example.newsfeed.util.showNoInternetToast
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.net.URLEncoder

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "FlowOperatorInvokedInComposition"
)
@Composable
fun NewsScreen(
    navController: NavController
) {
    val newsViewModel = hiltViewModel<NewsViewModel>()
    val newsPaging = newsViewModel.allNews.collectAsLazyPagingItems()

    val isRefresh by newsViewModel.isRefreshing.collectAsState()

    val networkViewModel = hiltViewModel<NetworkViewModel>()
    val isConnected by networkViewModel.isConnected.collectAsState()
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
            if (isConnected) {
                val encodedUrl = URLEncoder.encode(news.url, "UTF-8")
                navController.navigate(Screen.Details.route + "/$encodedUrl")
            } else {
                showNoInternetToast(context)
            }
        },
        swipeRefreshState = swipeRefreshState,
        newsPagingData = newsPaging
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
            newsPagingData.apply {

                when {
                    loadState.refresh is LoadState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    loadState.refresh is LoadState.Error -> {

                        val e = (loadState.refresh as LoadState.Error).error
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.load_error),
                                color = Color.Red,
                                fontSize = Dimens.FontSizeBox
                            )
                            Log.e(
                                "HomeScreen",
                                "Error during loading: ${e.localizedMessage}"
                            )
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.BottomCenter)
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val e = loadState.append as LoadState.Error
                        Text(
                            text = stringResource(R.string.update_error),
                            color = Color.Red
                        )
                        Log.e(
                            "HomeScreen",
                            "Error during updating: ${e.error.localizedMessage}"
                        )
                    }
                }
            }
        }
    }
}