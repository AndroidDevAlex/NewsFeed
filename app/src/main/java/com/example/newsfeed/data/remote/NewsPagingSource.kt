package com.example.newsfeed.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.presentation.entityUi.ItemNewsUi

class NewsPagingSource( private val newsDao: NewsDao) : PagingSource<Int, ItemNewsUi>() {
    override fun getRefreshKey(state: PagingState<Int, ItemNewsUi>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemNewsUi> {
        return try {
            val currentPageNumber = params.key ?: 1

            val pageSize = params.loadSize.coerceAtMost(PAGE_SIZE)

            val response = newsDao.getOllSavedNews((currentPageNumber - 1) * pageSize, pageSize)
                .map { it.mapFromDBToUi() }

            val prevKey = if (currentPageNumber == 1) null else currentPageNumber - 1
            val nextKey = if (response.isEmpty()) null else currentPageNumber + 1


            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}