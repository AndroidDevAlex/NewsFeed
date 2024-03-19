package com.example.newsfeed.modules

import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.data.remote.HabrServiceApi
import com.example.newsfeed.data.remote.RedditServiceApi
import com.example.newsfeed.data.remote.repository.BookmarkRepositoryImpl
import com.example.newsfeed.data.remote.repository.FilterRepositoryImpl
import com.example.newsfeed.data.remote.repository.NewsRepositoryImpl
import com.example.newsfeed.domain.repository.BookmarkRepository
import com.example.newsfeed.domain.repository.FilterRepository
import com.example.newsfeed.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModules {

    @Provides
    @ViewModelScoped
    fun provideBookmarkRepository(newsDao: NewsDao): BookmarkRepository {
        return BookmarkRepositoryImpl(newsDao)
    }

    @Provides
    @ViewModelScoped
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
    @ViewModelScoped
    fun provideFilterRepository(
        habrServiceApi: HabrServiceApi,
        redditServiceApi: RedditServiceApi
    ): FilterRepository {
        return FilterRepositoryImpl(
            redditServiceApi,
            habrServiceApi
        )
    }
}