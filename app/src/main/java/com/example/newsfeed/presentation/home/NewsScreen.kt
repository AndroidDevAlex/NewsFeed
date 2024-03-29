package com.example.newsfeed.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsfeed.presentation.ItemTemplate
import com.example.newsfeed.presentation.NewsUi

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsScreen(
    navController: NavController
) {

    val newsViewModel = hiltViewModel<NewsViewModel>()
    val state by newsViewModel.newsListNews.collectAsState()

    when (val currentState = state) {

        is NewsViewModel.StateViewModel.Success -> DrawContent(currentState.news)
        is NewsViewModel.StateViewModel.Error -> NewsWithError(currentState.news)
        is NewsViewModel.StateViewModel.Loading -> NewsDuringUpdate(currentState.news)
        NewsViewModel.StateViewModel.None -> NewsEmpty()
    }

    /* NewsScreenUi(
         bookMarkClick = {newsViewModel.onBookmarkClicked()},

         navigateToDetail = {navController.navigate(Screen.Details)}
     )*/
}

@Composable
fun NewsWithError(news: List<NewsUi>?) {
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
            DrawContent(news = news)
        }
    }
}

@Composable
fun NewsDuringUpdate(news: List<NewsUi>?) {
    Column {
        Box(
            Modifier
                .padding(10.dp)
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        if (news != null) {
            DrawContent(news = news)
        }
    }
}

@Composable
fun NewsEmpty() {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "No news")
    }
}

@Composable
private fun DrawContent(news: List<NewsUi>) {

    LazyColumn {
        itemsIndexed(news) { _, news ->
            key(news.id) {
                ItemTemplate(
                    item = news,
                    onItemClick = {

                    }) {

                }
            }
        }
    }
}