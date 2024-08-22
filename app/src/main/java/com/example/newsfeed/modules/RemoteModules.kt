package com.example.newsfeed.modules

import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.data.local.RoomDataSource
import com.example.newsfeed.data.remote.repository.BookmarkRepositoryImpl
import com.example.newsfeed.data.remote.repository.BookmarkRepository
import com.example.newsfeed.data.remote.repository.DetailRepositoryImpl
import com.example.newsfeed.data.remote.repository.DetailRepository
import com.example.newsfeed.data.remote.repository.HabrSource
import com.example.newsfeed.domain.useCase.bookmarkCase.BookmarkToggleUseCase
import com.example.newsfeed.domain.useCase.bookmarkCase.GetBookmarkNewsUseCase
import com.example.newsfeed.domain.useCase.homeCase.FetchNewsUseCase
import com.example.newsfeed.domain.useCase.homeCase.ToggleBookmarkUseCase
import com.example.newsfeed.data.remote.repository.NewsRepository
import com.example.newsfeed.data.remote.repository.NewsRepositoryImpl
import com.example.newsfeed.data.remote.repository.RedditSource
import com.example.newsfeed.domain.useCase.filterCase.GetAvailableSourcesUseCase
import com.example.newsfeed.domain.useCase.homeCase.GetAllNewsSourcesUseCase
import com.example.newsfeed.domain.useCase.filterCase.UpdateSelectedSourcesUseCase
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
    fun provideDetailRepository(
        roomDataSource: RoomDataSource
    ): DetailRepository {
        return DetailRepositoryImpl(
            roomDataSource
        )
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        dataSource: RoomDataSource,
        habrSource: HabrSource,
        redditSource: RedditSource
    ): NewsRepository {
        val listSources = listOf(habrSource, redditSource)
        return NewsRepositoryImpl(dataSource, listSources)
    }

    @Provides
    @Singleton
    fun provideGetAllNewsSourcesUseCase(
        newsRepository: NewsRepository
    ): GetAllNewsSourcesUseCase {
        return GetAllNewsSourcesUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateSelectedSourcesUseCase(
        newsRepository: NewsRepository
    ): UpdateSelectedSourcesUseCase {
        return UpdateSelectedSourcesUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideGetAvailableSourcesUseCase(
        newsRepository: NewsRepository
    ): GetAvailableSourcesUseCase {
        return GetAvailableSourcesUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideFetchNewsUseCase(
        newsRepository: NewsRepository
    ): FetchNewsUseCase {
        return FetchNewsUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideToggleBookmarkUseCase(
        newsRepository: NewsRepository
    ): ToggleBookmarkUseCase {
        return ToggleBookmarkUseCase(newsRepository)
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