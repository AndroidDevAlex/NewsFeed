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
    suspend fun insertNews(newsEntity:NewsDB)

    @Delete
    suspend fun deleteNews(newsEntity: NewsDB)

    @Query("SELECT * FROM news")
    fun getAllNews(): Flow<List<NewsDB>>
}