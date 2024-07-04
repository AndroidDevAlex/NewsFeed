package com.example.newsfeed.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsDB(
    @PrimaryKey val id: Long,
    val image: String?,
    val title: String,
    val publishedAt: String,
    val description: String,
    val addedBy: String,
    val isBookmarked: Boolean,
    val source: String,
    val url: String,
    val timeStamp: Long
)