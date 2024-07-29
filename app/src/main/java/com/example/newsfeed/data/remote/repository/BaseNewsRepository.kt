package com.example.newsfeed.data.remote.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsfeed.data.local.RoomDataSource
import com.example.newsfeed.data.remote.api.ApiManager
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.presentation.entityUi.NewsUi
import com.example.newsfeed.util.mapFromDBToUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class BaseNewsRepository(
    private val apiManager: ApiManager,
    private val dataSource: RoomDataSource
) : NewsRepository {

    abstract suspend fun fetchRemoteNews(): NewsUi

    override suspend fun fetchAndSaveNews() {
        val remoteNews = fetchRemoteNews()

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

    override fun getCombinedAndSortedNewsPagingSource(): Flow<PagingData<ItemNewsUi>> {
        val combinedNewsFlow =
            dataSource.getPagingCombinedNewsBySourcesSortedByDate()
                .map { pagingData ->
                    pagingData.map { it.mapFromDBToUi() }
                }
        return combinedNewsFlow
    }
}