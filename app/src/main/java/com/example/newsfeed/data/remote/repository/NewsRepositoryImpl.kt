package com.example.newsfeed.data.remote.repository


import com.example.newsfeed.data.remote.ApiManager
import com.example.newsfeed.data.remote.RoomDataSource
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.util.RequestResult
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dataSource: RoomDataSource,
    private val apiManager: ApiManager
) : NewsRepository {

    override suspend fun getOllNewsList(): RequestResult<List<NewsUi>> {
            return apiManager.getOllNewsList()
    }

    override suspend fun saveNews(news: NewsUi) {
        dataSource.saveNews(news)
    }

    override suspend fun deleteNews(news: NewsUi) {
        dataSource.deleteNews(news)
    }
}