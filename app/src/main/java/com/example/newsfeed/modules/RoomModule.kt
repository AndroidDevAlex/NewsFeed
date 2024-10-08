package com.example.newsfeed.modules

import android.content.Context
import androidx.room.Room
import com.example.newsfeed.data.local.DataBase
import com.example.newsfeed.data.local.NewsDao
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
        ).addMigrations(DataBase.MIGRATION_6_7)
            .build()
    }

    @Provides
    @Singleton
    fun getUserDao(database: DataBase): NewsDao {
        return database.getNewsDao()
    }
}