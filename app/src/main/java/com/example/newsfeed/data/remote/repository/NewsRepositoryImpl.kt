package com.example.newsfeed.data.remote.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsfeed.data.local.RoomDataSource
import com.example.newsfeed.data.remote.api.ApiManager
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.presentation.entityUi.NewsUi
import com.example.newsfeed.util.NewsSource
import com.example.newsfeed.util.mapFromDBToUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiManager: ApiManager,
    private val dataSource: RoomDataSource
) : NewsRepository {

    private val sourceHandlers: Map<NewsSource, suspend () -> NewsUi> = mapOf(
        NewsSource.HABR to { getHabrNews() },
        NewsSource.REDDIT to { getRedditNews() }
    )

    override suspend fun fetchAndSaveNews(sources: List<NewsSource>) {
        sources.forEach { source ->
            val fetchNews =
                sourceHandlers[source] ?: throw IllegalArgumentException("Unknown news source")

            val remoteNews = fetchNews()

            remoteNews.newsList.forEach { news ->
                val existingNews = dataSource.getNewsById(news.id)
                val isBookmarked = existingNews?.isBookmarked ?: false
                val timeStamp =
                    if (isBookmarked) existingNews?.timeStamp ?: System.currentTimeMillis() else 0

                val newsForSave = news.copy(
                    image = if (source == NewsSource.HABR) news.image
                        ?: remoteNews.defaultImage else news.image,
                    isBookmarked = isBookmarked,
                    timeStamp = timeStamp
                )
                dataSource.updateBookmarkStatus(newsForSave)
            }
        }
    }

    override suspend fun toggleBookmark(news: ItemNewsUi) {
        dataSource.updateBookmarkStatus(news)
    }

    override fun getCombinedAndSortedNewsPagingSource(sources: List<NewsSource>): Flow<PagingData<ItemNewsUi>> {
        val sourceNames = sources.map { it.sourceName }
        return dataSource.getPagingCombinedNewsBySourcesSortedByDate(sourceNames)
            .map { pagingData ->
                pagingData.map { it.mapFromDBToUi() }
            }
    }

    private suspend fun getHabrNews(): NewsUi {
        return apiManager.getAllHabrNewsList().apply {
            val defaultImage = this.defaultImage
            this.newsList.forEach { news ->
                if (news.image == null) {
                    news.image = defaultImage
                }
            }
        }
    }

    private suspend fun getRedditNews(): NewsUi {
        return apiManager.getAllRedditNewsList()
    }
}