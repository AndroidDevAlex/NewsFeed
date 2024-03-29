package com.example.newsfeed.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsEntity:NewsEntity): Long

    @Query("SELECT * FROM news WHERE id = :id")
    suspend fun getNewsById(id: Int): NewsEntity

    @Query("DELETE FROM news WHERE id = :id")
    suspend fun deleteNewsById(id: Int)

    @Query("SELECT * FROM news")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Delete
    suspend fun removeOll(news: List<NewsEntity>)

    @Query("DELETE FROM news")
    suspend fun clean()
}