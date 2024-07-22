package com.example.newsfeed.data.remote.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsfeed.data.local.RoomDataSource
import com.example.newsfeed.data.remote.api.ApiManager
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.util.NewsSource
import com.example.newsfeed.util.mapFromDBToUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RedditNewsRepositoryImpl @Inject constructor(
    private val apiManager: ApiManager,
    private val dataSource: RoomDataSource
) : NewsRepository {

    override suspend fun fetchAndSaveNews() {
        val remoteNews = apiManager.getOllRedditNewsList()

        remoteNews.newsList.forEach { news ->
            val existingNews = dataSource.getNewsById(news.id)
            val isBookmarked = existingNews?.isBookmarked ?: false
            val timeStamp =
                if (isBookmarked) existingNews?.timeStamp ?: System.currentTimeMillis() else 0

            val newsForSave = news.copy(
                isBookmarked = isBookmarked,
                timeStamp = timeStamp
            )
            dataSource.updateBookmarkStatus(newsForSave)
        }
    }

    override suspend fun toggleBookmark(news: ItemNewsUi) {
        dataSource.updateBookmarkStatus(news)
    }

    override fun getSavedNewsPagingSource(): Flow<PagingData<ItemNewsUi>> {
        return dataSource.getAllPagingSavedNewsByUserBySource(NewsSource.REDDIT.sourceName)
            .map { pagingDB ->
                pagingDB.map { it.mapFromDBToUi() }
            }
    }
}