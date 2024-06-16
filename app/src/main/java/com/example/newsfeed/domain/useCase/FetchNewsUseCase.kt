package com.example.newsfeed.domain.useCase

import androidx.paging.PagingData
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    suspend fun refreshNews(): Flow<PagingData<ItemNewsUi>> {
        repository.fetchAndSaveNews()
        return repository.getSavedNewsPagingSource()
    }
}