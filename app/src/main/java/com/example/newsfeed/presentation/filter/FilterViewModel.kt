package com.example.newsfeed.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.domain.useCase.filterCase.UpdateSelectedSourcesUseCase
import com.example.newsfeed.util.ConstantsSourceNames
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val updateSelectedSourcesUseCase: UpdateSelectedSourcesUseCase
) : ViewModel() {

    private val _selectedSources = MutableStateFlow<List<String>>(emptyList())
    val selectedSources: StateFlow<List<String>> = _selectedSources

    init {
        _selectedSources.value = listOf(ConstantsSourceNames.HABR, ConstantsSourceNames.REDDIT)
    }

    fun toggleSource(sourceName: String) {
        viewModelScope.launch {
            val currentSources = _selectedSources.value.toMutableList()
            if (currentSources.contains(sourceName)) {
                currentSources.remove(sourceName)
            } else {
                currentSources.add(sourceName)
            }
            _selectedSources.value = currentSources
            updateSelectedSourcesUseCase.updateNewsSource(currentSources)
        }
    }
}