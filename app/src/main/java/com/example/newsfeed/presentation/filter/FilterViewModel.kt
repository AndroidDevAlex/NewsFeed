package com.example.newsfeed.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.domain.useCase.filterCase.UpdateSelectedSourcesUseCase
import com.example.newsfeed.util.NewsSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val updateSelectedSourcesUseCase: UpdateSelectedSourcesUseCase
) : ViewModel() {

    private val _selectedSources = MutableStateFlow<List<NewsSource>>(emptyList())
    val selectedSources: StateFlow<List<NewsSource>> = _selectedSources

    init {
        _selectedSources.value = initialSources()
    }

    private fun initialSources(): List<NewsSource> {
        return listOf(NewsSource.HABR, NewsSource.REDDIT)
    }

    fun toggleSource(source: NewsSource) {
        viewModelScope.launch {
            val currentSources = _selectedSources.value.toMutableList()
            if (currentSources.contains(source)) {
                currentSources.remove(source)
            } else {
                currentSources.add(source)
            }
            _selectedSources.value = currentSources
            updateSelectedSourcesUseCase.updateNewsSource(currentSources)
        }
    }
}