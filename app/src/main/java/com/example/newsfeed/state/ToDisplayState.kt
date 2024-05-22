package com.example.newsfeed.state


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun NewsWithError() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.error_occurred),
            fontSize = 30.sp,
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

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