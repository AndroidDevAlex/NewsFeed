package com.example.newsfeed.domain.useCase.bookmarkCase

import com.example.newsfeed.domain.BookmarkRepository
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import javax.inject.Inject

class BookmarkToggleUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {

    suspend fun toggleBookmark(news: ItemNewsUi) {
        return repository.toggleBookmark(news)
    }
}