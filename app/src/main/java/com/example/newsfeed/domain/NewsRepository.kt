package com.example.newsfeed.domain


import androidx.paging.PagingData
import com.example.newsfeed.presentation.NewsUi
import kotlinx.coroutines.flow.Flow

interface NewsRepository{

    suspend fun fetchAndSaveNews()

    suspend fun saveNews(news: NewsUi)

    suspend fun deleteNews(news: NewsUi)

    fun getSavedNewsPagingSource(): Flow<PagingData<NewsUi>>
}