package com.example.newsfeed.data.remote

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
//import com.example.newsfeed.data.NewsPagingSource
import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.presentation.NewsUi

class RoomDataSource(
    private val newsDao: NewsDao
) {

     fun getPagingSavedNews() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = INITIAL_LOAD_SIZE,
            prefetchDistance = PREFETCH_DISTANCE
        ),
        // pagingSourceFactory = { NewsPagingSource(newsDao) }
         pagingSourceFactory = { newsDao.getNewsPagination() }
    ) .flow

    /*suspend fun getSavedNews(): List<NewsUi> {
        return try {
           val savedNews = newsDao.getAllNews().map { it.mapFromDBToUi() }
            savedNews
        } catch (e: Exception) {
            Log.e("log", " error occurred while receiving data $e")
            emptyList()
        }
    }*/

     suspend fun saveNews(news: NewsUi) {
        try {
            val newsEntity = news.mapToDB()
            newsDao.insertNews(newsEntity)
            Log.i("log", "News successfully saved in Local database")
        }catch (e: Exception){
            Log.e("log", "Error saving news: $e")
        }
    }

    suspend fun deleteNews(news: NewsUi) {
        try {
            val newsDB = news.mapToDB()
            newsDao.deleteNews(newsDB)
            Log.i("log", "News successfully removed from DB")
        }catch (e: Exception){
            Log.e("log", "error occurred while deleting data: ", e)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
        const val INITIAL_LOAD_SIZE = 40
        const val PREFETCH_DISTANCE = 10
    }
}