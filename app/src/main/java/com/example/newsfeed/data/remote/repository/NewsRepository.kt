package com.example.newsfeed.data.remote.repository

import androidx.paging.PagingData
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun fetchNewsFromApiAndSaveDB()

    suspend fun toggleBookmark(news: ItemNewsUi)

    fun getAllAvailableNewsBySource(): Flow<PagingData<ItemNewsUi>>

    fun updateSources(newsSources: List<String>)

    fun getSourcesNames(): List<String>
}