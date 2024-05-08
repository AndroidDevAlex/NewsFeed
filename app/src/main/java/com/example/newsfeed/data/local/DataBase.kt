package com.example.newsfeed.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    version = 2,
    entities = [
        NewsDB::class
    ]
)

abstract class DataBase: RoomDatabase() {

    object MIGRATION_1_2 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE news ADD COLUMN url TEXT")
        }
    }

    abstract fun getNewsDao():NewsDao
}