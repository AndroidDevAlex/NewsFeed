package com.example.newsfeed.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDBNews(@ApplicationContext context: Context): DataBase {
        return Room.databaseBuilder(
            context,
            DataBase::class.java,
            "local_database"
        ).addMigrations(DataBase.MIGRATION_4_5)
            .build()
    }

    @Provides
    @Singleton
    fun getUserDao(database: DataBase): NewsDao {
        return database.getNewsDao()
    }
}