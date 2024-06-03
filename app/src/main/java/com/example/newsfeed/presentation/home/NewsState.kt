package com.example.newsfeed.presentation.home

import androidx.paging.PagingData
import com.example.newsfeed.presentation.NewsUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class NewsState(
    val newList: Flow<PagingData<NewsUi>> = flowOf(PagingData.empty()),
    val isRefreshing: Boolean = false,
    val showDialog: Boolean = false
)