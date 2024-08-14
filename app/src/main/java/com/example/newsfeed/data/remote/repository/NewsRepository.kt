package com.example.newsfeed.data.remote.repository

import androidx.paging.PagingData
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.util.NewsSource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun fetchAndSaveNews(sources: List<NewsSource>)

    suspend fun toggleBookmark(news: ItemNewsUi)

    fun getCombinedAndSortedNewsPagingSource(sources: List<NewsSource>): Flow<PagingData<ItemNewsUi>>
}