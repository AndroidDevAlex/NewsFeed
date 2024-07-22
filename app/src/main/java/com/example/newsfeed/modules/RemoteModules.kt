package com.example.newsfeed.modules

import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.data.remote.api.ApiManager
import com.example.newsfeed.data.remote.api.habrApi.HabrServiceApi
import com.example.newsfeed.data.remote.api.redditApi.RedditServiceApi
import com.example.newsfeed.data.local.RoomDataSource
import com.example.newsfeed.data.remote.repository.BookmarkRepositoryImpl
import com.example.newsfeed.data.remote.repository.FilterRepositoryImpl
import com.example.newsfeed.data.remote.repository.HabrNewsRepositoryImpl
import com.example.newsfeed.data.remote.repository.BookmarkRepository
import com.example.newsfeed.data.remote.repository.FilterRepository
import com.example.newsfeed.data.remote.repository.NewsRepository
import com.example.newsfeed.data.remote.repository.DetailRepositoryImpl
import com.example.newsfeed.data.remote.repository.DetailRepository
import com.example.newsfeed.data.remote.repository.RedditNewsRepositoryImpl
import com.example.newsfeed.domain.useCase.bookmarkCase.BookmarkToggleUseCase
import com.example.newsfeed.domain.useCase.bookmarkCase.GetBookmarkNewsUseCase
import com.example.newsfeed.domain.useCase.homeCase.FetchNewsUseCase
import com.example.newsfeed.domain.useCase.homeCase.GetSavedCombineNewsUseCase
import com.example.newsfeed.domain.useCase.homeCase.ToggleBookmarkUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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
    @Named("Habr")
    fun provideNewsRepository(
        dataSource: RoomDataSource,
        apiManager: ApiManager
    ): NewsRepository {
        return HabrNewsRepositoryImpl(dataSource, apiManager)
    }

    @Provides
    @Singleton
    @Named("Reddit")
    fun provideRedditNewsRepository(
        dataSource: RoomDataSource,
        apiManager: ApiManager
    ): NewsRepository {
        return RedditNewsRepositoryImpl(apiManager, dataSource)
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
    fun provideFetchNewsUseCase(
        @Named("Habr") habrRepository: NewsRepository,
        @Named("Reddit") redditRepository: NewsRepository
    ): FetchNewsUseCase {
        return FetchNewsUseCase(habrRepository, redditRepository)
    }

    @Provides
    @Singleton
    fun provideToggleBookmarkUseCase(
        @Named("Habr") habrRepository: NewsRepository,
        @Named("Reddit") redditRepository: NewsRepository
    ): ToggleBookmarkUseCase {
        return ToggleBookmarkUseCase(habrRepository, redditRepository)
    }

    /*@Provides
    @Singleton
    fun provideGetSavedNewsUseCase(newsRepository: NewsRepository): GetSavedNewsUseCase {
        return GetSavedNewsUseCase(newsRepository)
    }*/

    @Provides
    @Singleton
    fun provideGetSavedCombineNewsUseCase(
        @Named("Habr") habrRepository: NewsRepository,
        @Named("Reddit") redditRepository: NewsRepository
    ): GetSavedCombineNewsUseCase {
        return GetSavedCombineNewsUseCase(habrRepository, redditRepository)
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