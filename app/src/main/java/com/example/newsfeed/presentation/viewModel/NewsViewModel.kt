package com.example.newsfeed.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.util.ResourceState
import com.example.newsfeed.data.remote.News
import com.example.newsfeed.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _newsListNews = MutableStateFlow<List<News>>(emptyList())
    val newsListNews = _newsListNews.asStateFlow()

    private val _selectedItem = MutableStateFlow<News?>(null)
    val selectedItem: StateFlow<News?> = _selectedItem

    init {
        fetchNewsList()
    }

    fun onNewsItemSelected(id: Int, source: String) {
        viewModelScope.launch(ioDispatcher) {
            newsRepository.getNews(id, source).collect { resourceState ->
                when (resourceState) {
                    is ResourceState.Success -> {
                        _selectedItem.value = resourceState.data
                        Log.i("tag", "Success")
                    }

                    is ResourceState.Loading -> {
                        Log.i("tag", "Loading data")
                    }

                    is ResourceState.Error -> {
                        Log.e("tag", "Error")
                    }
                }
            }
        }
    }

    private fun fetchNewsList() {
        viewModelScope.launch(ioDispatcher) {
            runCatching {
                val sources = listOf("habr", "reddit")
                newsRepository.getNewsList(sources).collect {
                    when (it) {
                        is ResourceState.Success -> {
                            _newsListNews.value = it.data!!
                        }

                        is ResourceState.Loading -> {
                            Log.i("tag", "Loading data")
                        }

                        is ResourceState.Error -> {
                            Log.e("tag", "Error")
                        }
                    }
                }
            }.onFailure { e ->
                Log.e("error", "$e")
            }
        }
    }

    fun onBookmarkClicked(news: News) {
        viewModelScope.launch(ioDispatcher) {
            if (news.isBookmarked) {
                newsRepository.deleteNews(news.id)
            } else {
                newsRepository.saveNews(news)
            }
        }
    }
}