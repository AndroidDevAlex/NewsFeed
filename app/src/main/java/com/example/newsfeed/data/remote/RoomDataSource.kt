package com.example.newsfeed.data.remote

import android.util.Log
import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.presentation.NewsUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class RoomDataSource(
    private val newsDao: NewsDao
) {

    fun getSavedNews(): Flow<List<NewsUi>> {
        return try {
            val savedNews = newsDao.getAllNews().map {
                it.mapFromDBToUi()
            }
            savedNews
        } catch (e: Exception) {
            Log.e("error", " error occurred while receiving data $e")
            flowOf(emptyList())
        }
    }

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
}