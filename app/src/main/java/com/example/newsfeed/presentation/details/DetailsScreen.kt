package com.example.newsfeed.presentation.details

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsfeed.util.AppTopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    navController: NavController,
    newsUrl: String
) {

    val detailViewModel = hiltViewModel<DetailViewModel>()
    val stateBookmark by detailViewModel.detailState.collectAsState()

    DetailsScreenUi(
        onRefresh = {},
        bookmarkState = stateBookmark,
        bookMarkClick = { },// detailViewModel.toggleBookmark()
        onBackPress = { navController.popBackStack() },
        newsUrl = newsUrl
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SetJavaScriptEnabled")
@Composable
private fun DetailsScreenUi(
    onRefresh: () -> Unit,
    bookmarkState: DetailState,
    bookMarkClick: () -> Unit,
    onBackPress: () -> Unit,
    newsUrl: String
) {

    Scaffold(topBar = {
        AppTopBar(
            pressClicked = { bookMarkClick() },
            onBackPress = { onBackPress },
            isBookmarked = bookmarkState.isBookmarked
        )
    }) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {

            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        webViewClient = WebViewClient()

                        settings.loadWithOverviewMode = true
                        settings.useWideViewPort = true
                        settings.setSupportZoom(true)
                    }
                },
                update = { webView ->

                    webView.loadUrl(newsUrl)
                }
            )

        }
    }
}