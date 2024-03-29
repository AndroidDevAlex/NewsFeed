package com.example.newsfeed.data.remote

import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.data.remote.repository.BookmarkRepositoryImpl
import com.example.newsfeed.data.remote.repository.FilterRepositoryImpl
import com.example.newsfeed.data.remote.repository.NewsRepositoryImpl
import com.example.newsfeed.domain.BookmarkRepository
import com.example.newsfeed.domain.FilterRepository
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.DispatchersModule
import com.example.newsfeed.presentation.home.GetAllNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModules {

    @Provides
    @Singleton
    fun provideBookmarkRepository(newsDao: NewsDao): BookmarkRepository {
        return BookmarkRepositoryImpl(newsDao)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        habrServiceApi: HabrServiceApi,
        redditServiceApi: RedditServiceApi,
        newsDao: NewsDao,
    ): NewsRepository {
        return NewsRepositoryImpl(
            habrServiceApi,
            redditServiceApi,
            newsDao,
            DispatchersModule.provideIoDispatcher()
        )
    }

    @Provides
    @Singleton
    fun provideFilterRepository(
        habrServiceApi: HabrServiceApi,
        redditServiceApi: RedditServiceApi
    ): FilterRepository {
        return FilterRepositoryImpl(
            redditServiceApi,
            habrServiceApi
        )
    }

    @Provides
    @Singleton
    fun provideNewsUseCase(newsRepository: NewsRepository): GetAllNewsUseCase {
        return GetAllNewsUseCase(newsRepository)
    }
}