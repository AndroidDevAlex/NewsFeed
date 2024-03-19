package com.example.newsfeed.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsfeed.presentation.viewModel.NewsViewModel
import com.example.newsfeed.util.AppTopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(onNavigateBack: () -> Unit) {
    //val newsViewModel: NewsViewModel = hiltViewModel()
    Scaffold(topBar = {
        AppTopBar(
            pressClicked = {
                onNavigateBack
            }
        )
    }) {

    }
}