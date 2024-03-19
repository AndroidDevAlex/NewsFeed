package com.example.newsfeed.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsfeed.R
import com.example.newsfeed.util.Headline
import com.example.newsfeed.presentation.ItemTemplate
import com.example.newsfeed.presentation.viewModel.NewsViewModel
import com.example.newsfeed.ui.theme.Orange
import com.example.newsfeed.util.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsScreen(onNavigateNext: () -> Unit) {

   val newsViewModel: NewsViewModel = hiltViewModel()

   val newsList by newsViewModel.newsListNews.collectAsState()

   Scaffold(
      topBar = {
         TopAppBar(
            title = {
               Text(
                  text = stringResource(id = R.string.app_name),
                  color = Color.White,
                  fontSize = Dimens.TopAppBarFontSize,
                  textAlign = TextAlign.Center,
                  modifier = Modifier.fillMaxWidth(),
                  fontWeight = FontWeight.Bold
               )
            }, colors = TopAppBarDefaults.smallTopAppBarColors(Orange)
         )
      }) {

      Column(Modifier.fillMaxHeight().padding(60.dp), horizontalAlignment = Alignment.Start) {
         Text(
            text = Headline.ALL_FEEDS.title,
            color = Color.DarkGray,
            fontSize = Dimens.TopAppBarFontSize,
            modifier = Modifier.padding(Dimens.Padding)
         )

         LazyColumn(
            Modifier
               .fillMaxHeight()
               .background(Color.Gray)
         ) {
            itemsIndexed(newsList) { _, items ->
               ItemTemplate(
                  item = items,
                  onItemClick = {
                        newsViewModel.onNewsItemSelected(items.id, items.source)
                     onNavigateNext
                    //  переход на DetailsScreen c помощью аргумента(id конкретного айтема)
                  },
                  onBookmarkClick = {
                     newsViewModel.onBookmarkClicked(items)
                  })
            }
         }
      }
   }
}