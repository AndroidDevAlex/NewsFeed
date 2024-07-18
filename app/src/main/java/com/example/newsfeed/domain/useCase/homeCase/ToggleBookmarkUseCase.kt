package com.example.newsfeed.domain.useCase.homeCase

import com.example.newsfeed.data.remote.repository.NewsRepository
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend fun toggleBookmark(news: ItemNewsUi) {
        return repository.toggleBookmark(news)
    }
}