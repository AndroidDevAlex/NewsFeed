package com.example.newsfeed.data.remote.repository


import com.example.newsfeed.data.remote.RoomDataSource
import com.example.newsfeed.domain.DetailRepository
import com.example.newsfeed.presentation.NewsUi
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val dataSource: RoomDataSource
) : DetailRepository {

    override suspend fun saveNews(news: NewsUi) {
        dataSource.saveNews(news)
    }

    override suspend fun deleteNews(news: NewsUi) {
        dataSource.deleteNews(news)
    }

    override suspend fun getNewsByUrl(newsUrl: String): NewsUi {
        return dataSource.getNewsByUrl(newsUrl)
    }
}