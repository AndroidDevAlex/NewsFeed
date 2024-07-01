package com.example.newsfeed.domain

import androidx.paging.PagingData
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    suspend fun toggleBookmark(news: ItemNewsUi)

    fun getSavedBookmarksNewsPagingSource(): Flow<PagingData<ItemNewsUi>>
}