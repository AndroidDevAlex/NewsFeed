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

   /* @Query("SELECT * FROM news")
    suspend fun getAllNews(): List<NewsDB>*/

   /* @Query("SELECT * FROM news LIMIT :limit OFFSET :id")
    suspend fun getNews(id: Int, limit: Int): List<NewsDB>*/

   /* @Query("SELECT * FROM news ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getNews(offset: Int, limit: Int): List<NewsDB>*/

   @Query("SELECT * FROM news ORDER BY id ASC")
     fun getNewsPagination(): PagingSource<Int, NewsDB>
}