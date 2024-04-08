package com.example.newsfeed.domain


import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.util.RequestResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository{

    fun getOllNewsList(): Flow<RequestResult<List<NewsUi>>>
   // fun getOllNewsList(vararg serviceApis: suspend () -> List<NewsUi>): Flow<RequestResult<List<NewsUi>>>

    fun getNewsById(id: Int, source: String): Flow<RequestResult<NewsUi>>

    suspend fun saveNews(news: NewsUi)

    suspend fun deleteNews(id: Int)

    suspend fun fetchLatest(): List<NewsUi>

    fun stateBookmark(id: Int): Flow<Boolean>
}