package com.example.newsfeed.domain.useCase.homeCase

import androidx.paging.PagingData
import com.example.newsfeed.data.remote.repository.NewsRepository
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNewsSourcesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    fun getAllNewsBySelectedSources(): Flow<PagingData<ItemNewsUi>> {
        return newsRepository.getAllAvailableNewsBySource()
    }
}