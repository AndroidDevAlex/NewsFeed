package com.example.newsfeed.domain.useCase.bookmarkCase

import androidx.paging.PagingData
import com.example.newsfeed.domain.BookmarkRepository
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkNewsUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {

    fun getNewsPagingSource(): Flow<PagingData<ItemNewsUi>> {
        return repository.getSavedBookmarksNewsPagingSource()
    }
}