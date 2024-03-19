package com.example.newsfeed.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        NewsEntity::class
    ]
)
abstract class DataBase: RoomDatabase() {

    abstract fun getNewsDao():NewsDao
}