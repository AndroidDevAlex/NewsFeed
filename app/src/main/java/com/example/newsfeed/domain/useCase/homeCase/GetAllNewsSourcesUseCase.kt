package com.example.newsfeed.domain.useCase.homeCase

import androidx.paging.PagingData
import com.example.newsfeed.data.remote.repository.NewsRepository
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.util.NewsSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNewsSourcesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    fun getNewsBySelectedSources(sources: List<NewsSource>): Flow<PagingData<ItemNewsUi>> {
        return newsRepository.getCombinedAndSortedNewsPagingSource(sources)
    }
}