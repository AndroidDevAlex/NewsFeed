package com.example.newsfeed.presentation.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsfeed.util.AppTopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreenUi(
    bookMarkClick: () -> Unit,
    onBackPress: () -> Unit,
) {
    Scaffold(topBar = {
        AppTopBar(
            pressClicked = { bookMarkClick },
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