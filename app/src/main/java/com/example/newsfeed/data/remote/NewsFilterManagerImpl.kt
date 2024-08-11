package com.example.newsfeed.data.remote

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsfeed.data.remote.repository.BaseNewsRepository
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.util.NewsSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class NewsFilterManagerImpl @Inject constructor(
    private val repositories: List<BaseNewsRepository>,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
) : NewsFilterManager {

    private val _selectedSources = MutableStateFlow(listOf(NewsSource.REDDIT, NewsSource.HABR))
    override val selectedSources: StateFlow<List<NewsSource>> = _selectedSources

    private val _filteredNews = MutableStateFlow<PagingData<ItemNewsUi>>(PagingData.empty())
    override val filteredNews: StateFlow<PagingData<ItemNewsUi>> = _filteredNews

    override fun toggleSource(source: NewsSource) {
        _selectedSources.value = _selectedSources.value.toMutableList().apply {
            if (contains(source)) remove(source) else add(source)
        }
        refreshNewsBySource(_selectedSources.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun refreshNewsBySource(source: List<NewsSource>) {
        CoroutineScope(ioDispatcher).launch {
            repositories
                .map { it.getCombinedAndSortedNewsPagingSource(source) }
                .asFlow()
                .flattenMerge()
                .cachedIn(this)
                .collect { filteredNews ->
                    _filteredNews.value = filteredNews
                }
        }
    }
}