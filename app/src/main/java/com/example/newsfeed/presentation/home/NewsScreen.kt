
package com.example.newsfeed.presentation.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsfeed.state.NewsDuringUpdate
import com.example.newsfeed.state.NewsEmpty
import com.example.newsfeed.state.ToDisplayState
import com.example.newsfeed.state.NewsWithError
import com.example.newsfeed.R
import com.example.newsfeed.navigation.Screen
import com.example.newsfeed.presentation.ErrorDialog
import com.example.newsfeed.presentation.ItemTemplate
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.state.StateUI
import com.example.newsfeed.ui.theme.Orange
import com.example.newsfeed.util.Dimens
import com.example.newsfeed.util.Headline
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.net.URLEncoder

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsScreen(
    navController: NavController
) {
    val newsViewModel = hiltViewModel<NewsViewModel>()
    val state by newsViewModel.newsListNews.collectAsState()

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)

    val newsPaging = newsViewModel.newsPagingDataFlow.collectAsLazyPagingItems()

    if (state.showDialog) {
        ErrorDialog(
            onDismiss = { newsViewModel.dismissErrorDialog() }
        )
    }

    NewsScreenUi(
        newsState = state,
        onRefresh = {
            swipeRefreshState.isRefreshing = true
            newsViewModel.refreshScreen()
        },
        bookMarkClick = {
            newsViewModel.pressBookmark(it)
        },
        navigateToDetail = { news ->
            val encodedUrl = URLEncoder.encode(news.url, "UTF-8")
            navController.navigate(Screen.Details.route + "/$encodedUrl")
        },
        swipeRefreshState = swipeRefreshState,
        newsPagingData = newsPaging,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun NewsScreenUi(
    onRefresh: () -> Unit,
    newsState: NewsState,
    bookMarkClick: (NewsUi) -> Unit,
    navigateToDetail: (NewsUi) -> Unit,
    swipeRefreshState: SwipeRefreshState,
    newsPagingData: LazyPagingItems<NewsUi>,
) {
     SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { onRefresh() }
    ) {
         Scaffold(
             topBar = {
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

                 when {
                     /*newsPagingData.loadState.refresh is LoadState.Loading -> {
                         CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                     }*/

                     newsPagingData.loadState.refresh is LoadState.Error -> {
                         val e = (newsPagingData.loadState.refresh as LoadState.Error).error
                         Text(
                             text = stringResource(R.string.load_error),
                             color = Color.Red
                         )
                         Log.e("HomeScreen", "Error during loading: ${e.localizedMessage}")

                     }
                 }
                     Text(
                         text = Headline.ALL_FEEDS.title,
                         color = Color.Black,
                         fontSize = Dimens.TopAppBarFontSize,
                         fontWeight = FontWeight.Bold
                     )

                 ToDisplayState(state = newsState.uiState) { state ->
                     when (state) {
                         is StateUI.Success -> {
                             LazyColumn {
                                 items(newsPagingData.itemCount) { index ->
                                     newsPagingData[index]?.let { news ->
                                         ItemTemplate(
                                             item = news,
                                             onItemClick = { navigateToDetail(it) },
                                             bookmarkClick = { bookMarkClick(it) },
                                             isBookmarked = news.isBookmarked
                                         )
                                     }
                                 }

                                 newsPagingData.apply {
                                     when {
                                         /*loadState.refresh is LoadState.Loading -> {
                                             item { CircularProgressIndicator() }
                                         }*/
                                         loadState.append is LoadState.Loading -> {
                                             item { CircularProgressIndicator() }
                                         }
                                         loadState.append is LoadState.Error -> {
                                             val e = loadState.append as LoadState.Error
                                             item {
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
                             is StateUI.Error -> {
                                 NewsWithError()
                             }

                             is StateUI.Loading -> {
                                 NewsDuringUpdate()
                             }

                             is StateUI.None -> {
                                 NewsEmpty()
                             }
                         }
                     }
                 }
         }
     }
}