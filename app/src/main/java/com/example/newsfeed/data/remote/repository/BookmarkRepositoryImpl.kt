package com.example.newsfeed.data.remote.repository


import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsfeed.data.remote.RoomDataSource
import com.example.newsfeed.data.remote.mapFromDBToUi
import com.example.newsfeed.domain.BookmarkRepository
import com.example.newsfeed.presentation.NewsUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val dataSource: RoomDataSource
) : BookmarkRepository {

    override suspend fun saveNews(news: NewsUi) {
        dataSource.saveNews(news)
    }

    override suspend fun deleteNews(news: NewsUi) {
        dataSource.deleteNews(news)
    }

    override fun getSavedNewsPagingSource(): Flow<PagingData<NewsUi>> {
        return dataSource.getPagingSavedNews().map {pagingDB ->
            pagingDB.map { it.mapFromDBToUi() }
        }
    }
}