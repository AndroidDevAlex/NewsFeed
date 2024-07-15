package com.example.newsfeed.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    version = 5,
    entities = [
        NewsDB::class
    ]
)

abstract class DataBase : RoomDatabase() {

    object MIGRATION_4_5 : Migration(4, 5) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
                CREATE TABLE news_new (
                    id INTEGER PRIMARY KEY NOT NULL,
                    image TEXT,
                    title TEXT NOT NULL,
                    publishedAt TEXT NOT NULL,
                    description TEXT NOT NULL,
                    addedBy TEXT NOT NULL,
                    isBookmarked INTEGER NOT NULL,
                    source TEXT NOT NULL,
                    url TEXT NOT NULL,
                    timeStamp INTEGER NOT NULL
                )
            """.trimIndent()
            )

            database.execSQL(
                """
                INSERT INTO news_new (id, image, title, publishedAt, description, addedBy, isBookmarked, source, url, timeStamp)
                SELECT id, image, title, publishedAt, description, addedBy, isBookmarked, source, url, timeStamp
                FROM news
            """.trimIndent()
            )

            database.execSQL("DROP TABLE news")
            database.execSQL("ALTER TABLE news_new RENAME TO news")
        }
    }

    abstract fun getNewsDao(): NewsDao
}