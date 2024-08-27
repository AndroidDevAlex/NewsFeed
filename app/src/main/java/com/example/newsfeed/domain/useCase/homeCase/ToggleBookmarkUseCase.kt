package com.example.newsfeed.domain.useCase.homeCase

import com.example.newsfeed.data.remote.repository.NewsRepository
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend fun toggleBookmark(news: ItemNewsUi) {
        newsRepository.toggleBookmark(news)
    }
}