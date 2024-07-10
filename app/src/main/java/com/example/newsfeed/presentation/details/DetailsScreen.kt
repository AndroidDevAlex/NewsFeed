package com.example.newsfeed.presentation.details

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
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
import com.example.newsfeed.internetConection.NetworkViewModel
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

    val networkViewModel = hiltViewModel<NetworkViewModel>()
    val isConnected by networkViewModel.isConnected.collectAsState()

    LaunchedEffect(newsUrl) {
        detailViewModel.loadNews(newsUrl)
    }

    DetailsScreenUi(
        uiState = state,
        isConnected = isConnected,
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
    isConnected: Boolean,
    bookMarkClick: () -> Unit,
    onBackPress: () -> Unit,
    newsUrl: String,
    context: Context,
    isBookmark: Boolean
) {
    val webView = remember {
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.setSupportZoom(true)
        }
    }

    if (isConnected) {
        webView.loadUrl(newsUrl)
    } else {
        Log.e("WebView", "No internet connection. URL not loaded: $newsUrl")
    }

    webView.webViewClient = object : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {
            uiState.stateUI = StateUI.Success
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
                    AndroidView(factory = { webView }, modifier = Modifier.then(
                        if (!isConnected) Modifier.graphicsLayer { alpha = 0.3f } else Modifier
                    ))
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