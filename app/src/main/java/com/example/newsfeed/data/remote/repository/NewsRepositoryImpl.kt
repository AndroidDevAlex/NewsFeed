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

    private val sourceMap: Map<String, Source> = availableSources.associateBy { it.getSourceName() }
    private var selectedSourceNames: Set<String> = sourceMap.keys

    override fun updateSources(newsSources: List<String>) {
        selectedSourceNames = newsSources.toSet()
    }

    override fun getSourcesNames(): List<String> {
        return sourceMap.keys.toList()
    }

    override fun getAllAvailableNewsBySource(): Flow<PagingData<ItemNewsUi>> {
        val selectedSourceNames = selectedSourceNames.toList()
        return dataSource.getPagingCombinedNewsBySourcesSortedByDate(
            selectedSourceNames
        ).map { pagingData ->
            pagingData.map { it.mapFromDBToUi() }
        }
    }

    override suspend fun fetchNewsFromApiAndSaveDB() {
        selectedSourceNames.forEach { sourceName ->
            val source = sourceMap[sourceName]
            source?.let {
                val remoteNews = source.fetchNews()

                remoteNews.newsList.forEach { news ->
                    val existingNews = dataSource.getNewsById(news.id)
                    val isBookmarked = existingNews?.isBookmarked ?: false
                    val timeStamp =
                        if (isBookmarked) existingNews?.timeStamp
                            ?: System.currentTimeMillis() else 0

                    val newsForSave = news.copy(
                        isBookmarked = isBookmarked,
                        timeStamp = timeStamp
                    )
                    dataSource.updateBookmarkStatus(newsForSave)
                }
            }
        }
    }

    override suspend fun toggleBookmark(news: ItemNewsUi) {
        dataSource.updateBookmarkStatus(news)
    }
}