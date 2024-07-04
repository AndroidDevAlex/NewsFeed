package com.example.newsfeed.domain.useCase.homeCase

import androidx.paging.PagingData
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    fun getNewsPagingSource(): Flow<PagingData<ItemNewsUi>> {
        return repository.getSavedNewsPagingSource()
    }
}