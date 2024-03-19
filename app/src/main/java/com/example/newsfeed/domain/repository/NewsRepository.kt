package com.example.newsfeed.domain.repository

import com.example.newsfeed.util.ResourceState
import com.example.newsfeed.data.remote.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository{

    fun getNewsList(sources: List<String>): Flow<ResourceState<List<News>>>

    fun getNews(id: Int, source: String): Flow<ResourceState<News>>

    suspend fun saveNews(news: News)

    suspend fun deleteNews(id: Int)
}