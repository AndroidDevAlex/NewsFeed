package com.example.newsfeed.domain.useCase.homeCase

import androidx.paging.PagingData
import com.example.newsfeed.data.remote.repository.NewsRepository
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject
import javax.inject.Named

class GetSavedCombineNewsUseCase @Inject constructor(
    @Named("Habr") private val habrRepository: NewsRepository,
    @Named("Reddit") private val redditRepository: NewsRepository
) {
    fun getCombinedNewsPagingSource(): Flow<PagingData<ItemNewsUi>> {
        val habrFlow: Flow<PagingData<ItemNewsUi>> = habrRepository.getSavedNewsPagingSource()
        val redditFlow: Flow<PagingData<ItemNewsUi>> = redditRepository.getSavedNewsPagingSource()

        return merge(habrFlow, redditFlow)
    }
}