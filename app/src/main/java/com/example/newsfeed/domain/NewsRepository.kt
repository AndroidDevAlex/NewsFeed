package com.example.newsfeed.domain


import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.util.RequestResult

interface NewsRepository{

    suspend fun getOllNewsList(): RequestResult<List<NewsUi>>

    suspend fun saveNews(news: NewsUi)

    suspend fun deleteNews(id: Int)


  // suspend fun fetchLatest(): RequestResult<List<NewsUi>>
}