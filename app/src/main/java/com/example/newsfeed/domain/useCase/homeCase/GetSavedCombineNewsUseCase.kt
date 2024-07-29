package com.example.newsfeed.domain.useCase.homeCase

import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsfeed.data.remote.repository.NewsRepository
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.util.NewsSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject
import javax.inject.Named

class GetSavedCombineNewsUseCase @Inject constructor(
    @Named("Habr") private val habrRepository: NewsRepository,
    @Named("Reddit") private val redditRepository: NewsRepository
) {

    fun getCombinedNewsPagingSource(): Flow<PagingData<ItemNewsUi>> {
        val sources = listOf(NewsSource.HABR, NewsSource.REDDIT)
        return merge(
            habrRepository.getCombinedAndSortedNewsPagingSource(sources),
            redditRepository.getCombinedAndSortedNewsPagingSource(sources)
        ).map { pagingData ->
            pagingData.map { it }
        }
    }
}