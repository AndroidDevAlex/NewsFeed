package com.example.newsfeed.data.remote.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsfeed.data.remote.api.ApiManager
import com.example.newsfeed.data.local.RoomDataSource
import com.example.newsfeed.util.mapFromDBToUi
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.util.NewsSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HabrNewsRepositoryImpl @Inject constructor(
    private val dataSource: RoomDataSource,
    private val apiManager: ApiManager
) : NewsRepository {

    override fun getSavedNewsPagingSource(): Flow<PagingData<ItemNewsUi>> {
        return dataSource.getAllPagingSavedNewsByUserBySource(NewsSource.HABR.sourceName)
            .map { pagingDB ->
                pagingDB.map { it.mapFromDBToUi() }
            }
    }

    override suspend fun fetchAndSaveNews() {
        val remoteNews = apiManager.getOllHabrNewsList()
        val defaultImageUrl = remoteNews.defaultImage

        remoteNews.newsList.forEach { news ->
            val existingNews = dataSource.getNewsById(news.id)
            val isBookmarked = existingNews?.isBookmarked ?: false
            val timeStamp =
                if (isBookmarked) existingNews?.timeStamp ?: System.currentTimeMillis() else 0

            val newsForSave = news.copy(
                image = news.image ?: defaultImageUrl,
                isBookmarked = isBookmarked,
                timeStamp = timeStamp
            )

            dataSource.updateBookmarkStatus(newsForSave)
        }
    }

    override suspend fun toggleBookmark(news: ItemNewsUi) {
        dataSource.updateBookmarkStatus(news)
    }
}