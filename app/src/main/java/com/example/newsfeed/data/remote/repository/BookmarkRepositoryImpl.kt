package com.example.newsfeed.data.remote.repository


import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsfeed.data.local.RoomDataSource
import com.example.newsfeed.util.mapFromDBToUi
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val dataSource: RoomDataSource
) : BookmarkRepository {

    override fun getSavedBookmarksNewsPagingSource(): Flow<PagingData<ItemNewsUi>> {
        return dataSource.getAllPagingSavedNewsByUser().map { pagingDB ->
            pagingDB.map { it.mapFromDBToUi() }
        }
    }

    override suspend fun toggleBookmark(news: ItemNewsUi) {
        dataSource.updateBookmarkStatus(news)
    }
}