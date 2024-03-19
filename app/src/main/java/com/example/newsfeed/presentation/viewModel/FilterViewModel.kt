package com.example.newsfeed.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.data.remote.News
import com.example.newsfeed.domain.repository.FilterRepository
import com.example.newsfeed.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val filterRepository: FilterRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _redditListNews: MutableStateFlow<List<ResourceState<News>>> = MutableStateFlow(
        listOf(ResourceState.Loading())
    )
    val redditListNews = _redditListNews.asStateFlow()

    init {
        getListRedditNews()
    }

    private fun getListRedditNews() {
        viewModelScope.launch(ioDispatcher) {
            filterRepository.getRedditNews().collect { redditNews ->
                _redditListNews.value = redditNews
            }
        }
    }
}