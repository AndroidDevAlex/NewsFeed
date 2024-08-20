package com.example.newsfeed.domain.useCase.filterCase

import com.example.newsfeed.data.remote.repository.NewsRepository
import javax.inject.Inject

class UpdateSelectedSourcesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    fun updateNewsSource(selectedSources: List<String>) {
        newsRepository.updateSources(selectedSources)
    }
}