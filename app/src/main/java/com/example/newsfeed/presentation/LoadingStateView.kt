package com.example.newsfeed.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.newsfeed.R
import com.example.newsfeed.util.Dimens

@Composable
fun LoadingStateView(
    loadState: CombinedLoadStates
) {
    when {
        loadState.refresh is LoadState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                DefaultCircularProgress()
            }
        }

        loadState.refresh is LoadState.Error -> {
            val e = (loadState.refresh as LoadState.Error).error

            DefaultErrorContent(textError = stringResource(R.string.load_error))

            Log.e(
                "LoadState",
                "Error during loading: ${e.localizedMessage}"
            )
        }

        loadState.append is LoadState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.BottomCenter)
            ) {
                DefaultCircularProgress()
            }
        }

        loadState.append is LoadState.Error -> {
            val e = loadState.append as LoadState.Error

            DefaultErrorContent(textError = stringResource(R.string.update_error))
            Log.e(
                "LoadState",
                "Error during updating: ${e.error.localizedMessage}"
            )
        }
    }
}

@Composable
private fun DefaultCircularProgress() {
    CircularProgressIndicator()
}

@Composable
private fun DefaultErrorContent(
    textError: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = textError,
            color = Color.Red,
            fontSize = Dimens.FontSizeBox
        )
    }
}