package com.example.newsfeed.presentation.bookmark

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.newsfeed.ui.theme.Blue
import com.example.newsfeed.ui.theme.DarkGray
import com.example.newsfeed.util.Dimens
import com.example.newsfeed.util.Headline
import com.example.newsfeed.util.showNoInternetToast
import java.net.URLEncoder

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookmarkScreen(navController: NavController) {

    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()

    val savedPagingNews = bookmarkViewModel.bookmarkedPagingDataFlow.collectAsLazyPagingItems()

    val networkViewModel = hiltViewModel<NetworkViewModel>()
    val isConnected by networkViewModel.isConnected.collectAsState()
    val context = LocalContext.current

    BookmarkScreenUi(
        bookmarkedNews = savedPagingNews,
        navigateToDetail = { news ->
            if (isConnected) {
                val encodedUrl = URLEncoder.encode(news.url, "UTF-8")
                navController.navigate(Screen.Details.route + "/$encodedUrl")
            } else {
                showNoInternetToast(context)
            }
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
    bookmarkedNews: LazyPagingItems<ItemNewsUi>,
    navigateToDetail: (ItemNewsUi) -> Unit,
    bookMarkClick: (ItemNewsUi) -> Unit
) {

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
                .padding(Dimens.Padding)
                .padding(bottom = Dimens.DistanceFromBottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(Dimens.SpacerHeight))

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
                    bookmarkedNews.apply {

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
                                Text(
                                    text = stringResource(R.string.load_error),
                                    color = Color.Red
                                )
                                Log.e(
                                    "BookMarkScreen",
                                    "Error during loading: ${e.localizedMessage}"
                                )
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
                                    "BookMarkScreen",
                                    "Error during updating: ${e.error.localizedMessage}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}