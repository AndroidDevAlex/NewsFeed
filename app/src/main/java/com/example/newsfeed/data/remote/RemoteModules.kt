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
import com.example.newsfeed.domain.useCase.bookmarkCase.BookmarkToggleUseCase
import com.example.newsfeed.domain.useCase.bookmarkCase.GetBookmarkNewsUseCase
import com.example.newsfeed.domain.useCase.homeCase.FetchNewsUseCase
import com.example.newsfeed.domain.useCase.homeCase.GetSavedNewsUseCase
import com.example.newsfeed.domain.useCase.homeCase.ToggleBookmarkUseCase
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
        redditServiceApi: RedditServiceApi
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
    fun provideFetchNewsUseCase(newsRepository: NewsRepository): FetchNewsUseCase {
        return FetchNewsUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideToggleBookmarkUseCase(newsRepository: NewsRepository): ToggleBookmarkUseCase {
        return ToggleBookmarkUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideGetSavedNewsUseCase(newsRepository: NewsRepository): GetSavedNewsUseCase {
        return GetSavedNewsUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideGetBookmarkNewsUseCase(newsRepository: BookmarkRepository): GetBookmarkNewsUseCase {
        return GetBookmarkNewsUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideBookmarkToggleUseCase(newsRepository: BookmarkRepository): BookmarkToggleUseCase {
        return BookmarkToggleUseCase(newsRepository)
    }
}