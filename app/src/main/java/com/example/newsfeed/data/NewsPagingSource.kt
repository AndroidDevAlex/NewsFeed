package com.example.newsfeed.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.data.remote.mapFromDBToUi
import com.example.newsfeed.presentation.NewsUi
/*
class NewsPagingSource(
    private val newsDao: NewsDao
) : PagingSource<Int, NewsUi>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsUi> {
        return try {

            val idNews: Int = params.key ?: 1
            val amountNews = 20

            val response = newsDao.getNews(idNews, amountNews).map { it.mapFromDBToUi() }

            val prevId = if (idNews > 0) idNews - 1 else null
            val nextId = if (response.isNotEmpty()) idNews + 1 else null

            LoadResult.Page(
                data = response,
                prevKey = prevId,
                nextKey = nextId
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsUi>): Int? {
        return state.anchorPosition
    }
}*/