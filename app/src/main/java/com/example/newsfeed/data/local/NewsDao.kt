package com.example.newsfeed.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsEntity: NewsDB)

    @Query("SELECT * FROM news WHERE url = :newsUrl LIMIT 1")
    suspend fun getNewsByUrl(newsUrl: String): NewsDB

    @Query("SELECT * FROM news WHERE id = :id LIMIT 1")
    suspend fun getNewsById(id: Long): NewsDB?

    @Query("SELECT * FROM news WHERE isBookmarked = 1 ORDER BY timeStamp DESC")
    fun getSavedNewsPaginationByUser(): PagingSource<Int, NewsDB>

    @Query("SELECT * FROM news ORDER BY id DESC")
    fun getAllSavedNews(): PagingSource<Int, NewsDB>

}