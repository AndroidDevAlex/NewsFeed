package com.example.newsfeed.presentation.details

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsfeed.R
import com.example.newsfeed.presentation.details.state.StateUI
import com.example.newsfeed.ui.theme.Blue
import com.example.newsfeed.ui.theme.DarkGray
import com.example.newsfeed.ui.theme.Purple
import com.example.newsfeed.util.Dimens

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
        bookMarkClick = { detailViewModel.toggleBookmark() },
        onBackPress = { navController.popBackStack() },
        newsUrl = newsUrl,
        context = context,
        isBookmark = state.isBookmarked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                uiState.stateUI = StateUI.Error
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.e("WebView", "Web page loading error: ${error?.description}")
                }
            }

            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                uiState.stateUI = StateUI.Error
                Log.e("WebView", "HTTP error: ${errorResponse?.statusCode}")
            }
        }
        webView.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.setSupportZoom(true)
            loadUrl(newsUrl)
        }
    }
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = Blue,
                    textAlign = TextAlign.Center,
                    fontSize = Dimens.TopAppBarFontSize,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
            },
            colors = topAppBarColors(
                DarkGray
            ),
            navigationIcon = {
                IconButton(
                    onClick = onBackPress,
                    modifier = Modifier.padding(start = Dimens.ModifierPadding)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = bookMarkClick,
                    modifier = Modifier.padding(end = Dimens.ModifierPadding)
                ) {
                    val iconTint = if (isBookmark) Purple else Color.White
                    Icon(
                        painter = painterResource(id = R.drawable.bookmark),
                        contentDescription = "Bookmark",
                        tint = iconTint
                    )
                }
            }
        )
    }) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = Dimens.BetweenItemsTop)
        ) {
            Spacer(modifier = Modifier.height(Dimens.Height))

            when (uiState.stateUI) {
                StateUI.Loading -> {
                    Column {
                        Box(
                            Modifier
                                .padding(Dimens.PaddingProgress)
                                .fillMaxWidth(), contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                StateUI.Success -> {
                    AndroidView(factory = { webView })
                }

                StateUI.Error -> {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(Dimens.PaddingProgress),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.error),
                            color = Color.Red,
                            fontSize = Dimens.FontSizeBox
                        )
                    }
                }
            }
        }
    }
}