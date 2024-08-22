package com.example.newsfeed.data.remote.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsfeed.data.local.RoomDataSource
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.util.mapFromDBToUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dataSource: RoomDataSource,
    private val availableSources: List<Source>
) : NewsRepository {

    private var selectedSources: List<Source> = availableSources

    override fun updateSources(newsSources: List<String>) {
        selectedSources = availableSources.filter { source ->
            newsSources.contains(source.getSourceName())
        }
    }

    override fun getSourcesNames(): List<String> {
        return listOf(SOURCE_REDDIT, SOURCE_HABR)
    }

    override fun getCombinedAndSortedNewsPagingSource(): Flow<PagingData<ItemNewsUi>> {
        return dataSource.getPagingCombinedNewsBySourcesSortedByDate(
            selectedSources.map { it.getSourceName() }
        ).map { pagingData ->
            pagingData.map { it.mapFromDBToUi() }
        }
    }

    override suspend fun fetchAndSaveNews() {
        selectedSources.forEach { source ->
            val remoteNews = source.fetchNews()

            remoteNews.newsList.forEach { news ->
                val existingNews = dataSource.getNewsById(news.id)
                val isBookmarked = existingNews?.isBookmarked ?: false
                val timeStamp =
                    if (isBookmarked) existingNews?.timeStamp ?: System.currentTimeMillis() else 0

                val newsForSave = news.copy(
                    image = if (source.getSourceName() == SOURCE_HABR) news.image
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

    companion object {
        private const val SOURCE_REDDIT = "reddit"
        private const val SOURCE_HABR = "habr"
    }
}