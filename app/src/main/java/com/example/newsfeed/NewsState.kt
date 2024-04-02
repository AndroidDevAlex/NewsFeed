package com.example.newsfeed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.presentation.home.NewsViewModel

@Composable
fun NewsState(
    state: NewsViewModel.StateViewModel,
    onRetry: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
   when (state) {
        is NewsViewModel.StateViewModel.Success -> content
        is NewsViewModel.StateViewModel.Error -> NewsWithError(state.news, onRetry)
        is NewsViewModel.StateViewModel.Loading -> NewsDuringUpdate(state.news, onRetry)
        NewsViewModel.StateViewModel.None -> NewsEmpty()
    }
}

@Composable
 fun NewsWithError(
    news: List<NewsUi>?,
    onRetry: (() -> Unit)?) {
    Column {
        Box(
            Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.error),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Error during update", color = MaterialTheme.colorScheme.onError)
        }
        if (news != null) {
            onRetry
        }
    }
}

@Composable
fun NewsDuringUpdate(news: List<NewsUi>?, onRetry: (() -> Unit)?) {
    Column {
        Box(
            Modifier
                .padding(10.dp)
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        if (news != null) {
            onRetry
        }
    }
}

@Composable
 fun NewsEmpty() {
    Box(contentAlignment = Alignment.Center) {
        Text(text = stringResource(R.string.no_news))
    }
}