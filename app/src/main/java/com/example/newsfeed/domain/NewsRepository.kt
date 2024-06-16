package com.example.newsfeed.domain


import androidx.paging.PagingData
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import kotlinx.coroutines.flow.Flow

interface NewsRepository{

    suspend fun fetchAndSaveNews()

    suspend fun toggleBookmark(news: ItemNewsUi)

    fun getSavedNewsPagingSource(): Flow<PagingData<ItemNewsUi>>
}