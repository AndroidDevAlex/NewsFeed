package com.example.newsfeed.presentation.details

import android.annotation.SuppressLint
import android.webkit.CookieManager
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.graphicsLayer
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
import com.example.newsfeed.ui.theme.Purple
import com.example.newsfeed.util.Dimens

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun DetailsScreen(
    navController: NavController,
    newsUrl: String
) {

    val detailViewModel = hiltViewModel<DetailViewModel>()
    val state by detailViewModel.detailState.collectAsState()

    val isConnected by detailViewModel.isConnected.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(newsUrl) {
        detailViewModel.loadNews(newsUrl)
    }

    val webView = remember {
        CustomWebView(context).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.setSupportZoom(true)
            settings.userAgentString

            clearCache(true)

            configureCookieManager(newsUrl)

            loadUrl(newsUrl)
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    detailViewModel.updateStateUi(StateUI.Success)
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    val url = request?.url.toString()

                    configureCookieManager(url)

                    view?.loadUrl(url)
                    return true
                }

                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
                ) {
                    detailViewModel.updateStateUi(StateUI.Error)
                }

            }
            onScrollChangedListener = { newScrollPosition ->
                detailViewModel.updateScrollPosition(newScrollPosition)
            }

            post {
                scrollTo(0, state.scrollPosition)
            }
        }
    }

    DetailsScreenUi(
        uiState = state,
        isConnected = isConnected,
        webView = webView,
        bookMarkClick = { detailViewModel.toggleBookmark() },
        onBackPress = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun DetailsScreenUi(
    uiState: DetailState,
    isConnected: Boolean,
    webView: CustomWebView,
    bookMarkClick: () -> Unit,
    onBackPress: () -> Unit
) {
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
                    val iconTint = if (uiState.isBookmarked) Purple else Color.White
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
                    AndroidView(
                        factory = {
                            webView
                        },
                        Modifier.then(
                            if (!isConnected) Modifier.graphicsLayer { alpha = 0.3f } else Modifier
                        )
                    )
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

private fun configureCookieManager(url: String) {
    val cookieManager = CookieManager.getInstance()
    if (url.contains("https://www.reddit.com")) {
        cookieManager.setAcceptCookie(true)
        cookieManager.getCookie(url)
    } else {
        cookieManager.setAcceptCookie(false)
    }
}