package com.example.newsfeed.domain.useCase


import androidx.paging.PagingData
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.presentation.NewsUi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    fun getSavedNewsPaging(): Flow<PagingData<NewsUi>> {
        return repository.getSavedNewsPagingSource()
    }

    suspend fun invoke() = repository.fetchAndSaveNews()
}