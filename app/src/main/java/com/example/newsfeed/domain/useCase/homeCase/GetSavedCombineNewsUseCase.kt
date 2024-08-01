package com.example.newsfeed.domain.useCase.homeCase

import androidx.paging.PagingData
import com.example.newsfeed.data.remote.repository.BaseNewsRepository
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.util.NewsSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flattenMerge
import javax.inject.Inject

class GetSavedCombineNewsUseCase @Inject constructor(
    private val repositories: List<BaseNewsRepository>
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCombinedNewsPagingSource(): Flow<PagingData<ItemNewsUi>> {
        val sources = listOf(NewsSource.HABR, NewsSource.REDDIT)

        val listFlows = repositories.map { it.getCombinedAndSortedNewsPagingSource(sources) }

        return listFlows.asFlow().flattenMerge()
    }
}