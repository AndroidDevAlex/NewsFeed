package com.example.newsfeed.domain

import com.example.newsfeed.presentation.NewsUi
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    fun getSavedNews(): Flow<List<NewsUi>>

    suspend fun saveNews(news: NewsUi)

    suspend fun deleteNews(news: NewsUi)
}