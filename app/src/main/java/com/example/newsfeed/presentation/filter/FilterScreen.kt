package com.example.newsfeed.presentation.filter

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilterScreen() {

    val redditNewsViewModel: FilterViewModel = hiltViewModel()

    FilterScreenUi(
        clickItem = { source ->{
            when(source){
                "reddit" -> {

                }
                "habr" -> {

                }
            }
        }

        }
    )
}