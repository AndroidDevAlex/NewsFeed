package com.example.newsfeed.presentation.details

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsfeed.presentation.details.state.StateUI
import com.example.newsfeed.util.AppTopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    navController: NavController,
    newsUrl: String
) {

    val detailViewModel = hiltViewModel<DetailViewModel>()
    val state by detailViewModel.detailState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(newsUrl) {
        detailViewModel.loadNews(newsUrl)
    }

    DetailsScreenUi(
        uiState = state,
        bookMarkClick = {detailViewModel.toggleBookmark()},
        onBackPress = { navController.popBackStack() },
        newsUrl = newsUrl,
        context = context,
        isBookmark = state.isBookmarked
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SetJavaScriptEnabled")
@Composable
private fun DetailsScreenUi(
    uiState: DetailState,
    bookMarkClick: () -> Unit,
    onBackPress: () -> Unit,
    newsUrl: String,
    context: Context,
    isBookmark: Boolean
) {

    val webView = remember { WebView(context) }

    webView.apply {

        webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                uiState.stateUI = StateUI.Success
            }
        }
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.setSupportZoom(true)
        loadUrl(newsUrl)
    }

    Scaffold(topBar = {
        AppTopBar(
            pressBookmark = {
                bookMarkClick()
            },
            onBackPress = onBackPress,
            isBookmarked = isBookmark
        )
    }) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 35.dp)
        ) {
            Spacer(modifier = Modifier.height(25.dp))

            when(uiState.stateUI){
                StateUI.Loading -> {
                    Column {
                        Box(
                            Modifier
                                .padding(30.dp)
                                .fillMaxWidth(), contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                StateUI.Success -> {
                    AndroidView(factory = { webView })
                }
            }
        }
    }
}