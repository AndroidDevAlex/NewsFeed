package com.example.newsfeed.domain


import androidx.paging.PagingData
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.util.RequestResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository{

  //  suspend fun getSavedNews(): List<NewsUi>

    suspend fun fetchAndSaveNews(): RequestResult<List<NewsUi>>

    suspend fun saveNews(news: NewsUi)

    suspend fun deleteNews(news: NewsUi)

    fun getSavedNewsPagingSource(): Flow<PagingData<NewsUi>>
}