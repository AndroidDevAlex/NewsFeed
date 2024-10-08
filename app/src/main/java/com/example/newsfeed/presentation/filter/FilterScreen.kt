package com.example.newsfeed.presentation.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsfeed.util.Dimens
import com.example.newsfeed.util.Headline
import com.example.newsfeed.presentation.SourceButton
import com.example.newsfeed.util.TopBarCustom

@Composable
fun FilterScreen() {
    val filterViewModel: FilterViewModel = hiltViewModel()

    val selectedSources by filterViewModel.selectedSources.collectAsState()

    FilterScreenUi(
        pressSource = { source ->
            filterViewModel.toggleSource(source)
        },
        selectedSources = selectedSources
    )
}

@Composable
fun FilterScreenUi(
    selectedSources: List<String>,
    pressSource: (String) -> Unit
) {

    val newsSourcesList = listOf(FilterSourceNames.HABR_SOURCE, FilterSourceNames.REDDIT_SOURCE)

    TopBarCustom {

        Text(
            text = Headline.ALL_GROUPS.title,
            color = Color.Black,
            fontSize = Dimens.HeadlineSize,
            modifier = Modifier.padding(Dimens.Padding),
            fontWeight = FontWeight.ExtraBold
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            Modifier
                .background(Color.White),
            contentPadding = PaddingValues(Dimens.PaddingProgress),
            horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingHorizontal),
            verticalArrangement = Arrangement.spacedBy(Dimens.PaddingVertical)
        ) {
            itemsIndexed(newsSourcesList) { _, source ->
                val isSelected = selectedSources.contains(source)

                SourceButton(
                    source = source,
                    isSelected = isSelected,
                    onClick = {
                        pressSource(source)
                    },
                    modifier = Modifier
                        .padding(start = Dimens.PaddingMod)
                        .padding(end = Dimens.PaddingMod)
                        .padding(top = Dimens.PaddingModifier),
                    isBookmarkScreen = false
                )
            }
        }
    }
}

private object FilterSourceNames {
    const val REDDIT_SOURCE = "reddit"
    const val HABR_SOURCE = "habr"
}