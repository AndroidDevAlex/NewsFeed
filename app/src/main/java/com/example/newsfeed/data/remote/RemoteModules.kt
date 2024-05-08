package com.example.newsfeed.data.remote

import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.data.remote.repository.BookmarkRepositoryImpl
import com.example.newsfeed.data.remote.repository.FilterRepositoryImpl
import com.example.newsfeed.data.remote.repository.NewsRepositoryImpl
import com.example.newsfeed.domain.BookmarkRepository
import com.example.newsfeed.domain.FilterRepository
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.data.remote.repository.DetailRepositoryImpl
import com.example.newsfeed.domain.DetailRepository
import com.example.newsfeed.presentation.home.GetAllNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModules {

    @Provides
    @Singleton
    fun provideBookmarkRepository(roomDataSource: RoomDataSource): BookmarkRepository {
        return BookmarkRepositoryImpl(roomDataSource)
    }

    @Provides
    @Singleton
    fun provideRoomDataSource(newsDao: NewsDao): RoomDataSource {
        return RoomDataSource(newsDao)
    }

    @Provides
    @Singleton
    fun provideApiManager(
        habrServiceApi: HabrServiceApi,
        redditServiceApi: RedditServiceApi,
    ): ApiManager {
        return ApiManager(habrServiceApi, redditServiceApi)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        dataSource: RoomDataSource,
        apiManager: ApiManager
    ): NewsRepository {
        return NewsRepositoryImpl(dataSource, apiManager)
    }

    @Provides
    @Singleton
    fun provideDetailRepository(
        roomDataSource: RoomDataSource
    ): DetailRepository {
        return DetailRepositoryImpl(
            roomDataSource
        )
    }

    @Provides
    @Singleton
    fun provideFilterRepository(): FilterRepository {
        return FilterRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideNewsUseCase(newsRepository: NewsRepository): GetAllNewsUseCase {
        return GetAllNewsUseCase(newsRepository)
    }
}