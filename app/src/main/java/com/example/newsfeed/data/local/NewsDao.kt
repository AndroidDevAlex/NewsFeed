package com.example.newsfeed.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsEntity: NewsDB)

    @Delete
    suspend fun deleteNews(newsEntity: NewsDB)

    @Query("SELECT * FROM news ORDER BY id DESC")
    fun getNewsPagination(): PagingSource<Int, NewsDB>

    @Query("SELECT * FROM news WHERE url = :newsUrl LIMIT 1")
    suspend fun getNewsByUrl(newsUrl: String): NewsDB

    @Query("SELECT * FROM news WHERE id = :id LIMIT 1")
    suspend fun getNewsById(id: Long): NewsDB
}