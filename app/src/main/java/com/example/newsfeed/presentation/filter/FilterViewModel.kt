package com.example.newsfeed.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.presentation.NewsSourceManager
import com.example.newsfeed.util.NewsSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val newsSourceManager: NewsSourceManager
) : ViewModel() {

    val selectedSources: StateFlow<List<NewsSource>> = newsSourceManager.selectedSources

    fun toggleSource(source: NewsSource) {
        viewModelScope.launch {
            val currentSources = selectedSources.value.toMutableList()
            if (currentSources.contains(source)) {
                currentSources.remove(source)
            } else {
                currentSources.add(source)
            }
            newsSourceManager.setSelectedSources(currentSources)
        }
    }
}