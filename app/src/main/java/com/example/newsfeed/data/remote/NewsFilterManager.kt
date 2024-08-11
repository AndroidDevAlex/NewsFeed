package com.example.newsfeed.data.remote

import androidx.paging.PagingData
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.util.NewsSource
import kotlinx.coroutines.flow.StateFlow

interface NewsFilterManager {

    val selectedSources: StateFlow<List<NewsSource>>
    val filteredNews: StateFlow<PagingData<ItemNewsUi>>

    fun toggleSource(source: NewsSource)

    fun refreshNewsBySource(source: List<NewsSource>)
}