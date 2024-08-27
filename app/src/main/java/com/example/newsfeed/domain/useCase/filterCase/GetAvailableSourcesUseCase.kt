package com.example.newsfeed.domain.useCase.filterCase

import com.example.newsfeed.data.remote.repository.NewsRepository
import javax.inject.Inject

class GetAvailableSourcesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    fun getAvailableSources(): List<String> {
        return newsRepository.getSourcesNames()
    }
}