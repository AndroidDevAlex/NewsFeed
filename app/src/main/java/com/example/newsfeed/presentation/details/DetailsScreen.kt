package com.example.newsfeed.presentation.details

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(navController: NavController) {

    DetailsScreenUi(
        bookMarkClick = {},
        onBackPress = {
            navController.popBackStack()
        }
    )
}