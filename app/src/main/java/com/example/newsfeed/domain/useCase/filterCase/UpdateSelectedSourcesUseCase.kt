package com.example.newsfeed.domain.useCase.filterCase

import com.example.newsfeed.data.remote.repository.NewsRepository
import com.example.newsfeed.util.NewsSource
import javax.inject.Inject

class UpdateSelectedSourcesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    fun updateNewsSource(selectedSources: List<NewsSource>) {
        newsRepository.updateSources(selectedSources)
    }
}