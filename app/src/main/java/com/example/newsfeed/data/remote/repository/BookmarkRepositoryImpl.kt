package com.example.newsfeed.data.remote.repository


import com.example.newsfeed.data.remote.RoomDataSource
import com.example.newsfeed.domain.BookmarkRepository
import com.example.newsfeed.presentation.NewsUi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val dataSource: RoomDataSource
) : BookmarkRepository {

    override fun getSavedNews(): Flow<List<NewsUi>> {
         return dataSource.getSavedNews()
    }

    override suspend fun saveNews(news: NewsUi) {
       dataSource.saveNews(news)
    }

    override suspend fun deleteNews(news: NewsUi) {
       dataSource.deleteNews(news)
    }
}