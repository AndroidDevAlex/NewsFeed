package com.example.newsfeed.domain

import com.example.newsfeed.presentation.NewsUi

interface DetailRepository {

    suspend fun saveNews(news: NewsUi)

    suspend fun deleteNews(news: NewsUi)

    suspend fun getNewsByUrl(newsUrl: String): NewsUi
}