package com.example.newsfeed.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    version = 3,
    entities = [
        NewsDB::class
    ]
)

abstract class DataBase : RoomDatabase() {

    object MIGRATION_2_3 : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE news ADD COLUMN timeStamp INTEGER DEFAULT 0")

        }
    }

    abstract fun getNewsDao(): NewsDao
}