package com.example.newsfeed.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    val image: String,
    val title: String,
    val publishedAt: Int,
    val description: String,
    val addedBy: String,
    val isBookmarked: Boolean,
    val source: String
)