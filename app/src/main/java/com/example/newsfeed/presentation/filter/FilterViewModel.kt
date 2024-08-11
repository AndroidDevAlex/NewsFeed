package com.example.newsfeed.presentation.filter

import androidx.lifecycle.ViewModel
import com.example.newsfeed.data.remote.NewsFilterManager
import com.example.newsfeed.util.NewsSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val newsFilterManager: NewsFilterManager
) : ViewModel() {

    val selectedSources: StateFlow<List<NewsSource>> = newsFilterManager.selectedSources

    fun toggleSource(source: NewsSource) {
        newsFilterManager.toggleSource(source)
    }
}