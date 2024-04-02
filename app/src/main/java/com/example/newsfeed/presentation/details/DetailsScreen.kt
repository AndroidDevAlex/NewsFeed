package com.example.newsfeed.presentation.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsfeed.util.AppTopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(navController: NavController) {

    val detailViewModel = hiltViewModel<DetailViewModel>()

    val state by detailViewModel.detailState.collectAsState()

    DetailsScreenUi(
        state,
        bookMarkClick = {
        detailViewModel.toggleBookmark()
                        },
        onBackPress = {
            navController.popBackStack()
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun DetailsScreenUi(
    state: DetailState,
    bookMarkClick: () -> Unit,
    onBackPress: () -> Unit,
) {
    Scaffold(topBar = {
        AppTopBar(
            pressClicked = { bookMarkClick() },
            onBackPress = {onBackPress}
        )
    }) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {





        }
    }
}