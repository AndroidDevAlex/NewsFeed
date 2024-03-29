package com.example.newsfeed.presentation.filter

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.newsfeed.R
import com.example.newsfeed.ui.theme.Orange
import com.example.newsfeed.util.Dimens
import com.example.newsfeed.util.Headline
import com.example.newsfeed.util.SourceButton

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilterScreenUi(clickItem: (String) -> Unit) {

    val newsList = listOf("reddit", "habr")

    Scaffold(topBar = {
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

        Column(
            Modifier
                .fillMaxHeight()
                .padding(60.dp), horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = Headline.ALL_GROUPS.title,
                color = Color.DarkGray,
                fontSize = Dimens.TopAppBarFontSize,
                modifier = Modifier.padding(Dimens.Padding)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                Modifier
                    .background(Color.White)
                    .padding(Dimens.Padding)
            ) {

                itemsIndexed(newsList) { index, source ->
                    SourceButton(
                        source = source,
                        onClick = {
                            clickItem(source)
                          /*  if (newsList[index] == "reddit") {
                                // redditNewsViewModel.redditListNews
                            } else {
                                // и также запуск HabrNews
                            }*/
                        }
                    )
                }

            }
        }
    }
}