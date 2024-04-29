package com.example.newsfeed.state


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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.newsfeed.R

@Composable
fun <T> ToDisplayState(
    state: T,
    content: @Composable (T) -> Unit
) {
    when (state) {
        is StateUI.Loading -> NewsDuringUpdate()
        is StateUI.Success -> content(state)
        is StateUI.Error -> NewsWithError()
        StateUI.None -> NewsEmpty()
    }
}

@Composable
fun NewsWithError(
) {
    Column {
        Box(
            Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.error),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.error_during_update),
                color = MaterialTheme.colorScheme.onError
            )
        }
    }
}

@Composable
fun NewsDuringUpdate() {
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

@Composable
fun NewsEmpty() {
    Box(contentAlignment = Alignment.Center) {
        Text(text = stringResource(R.string.no_news), color = Color.Red)
    }
}