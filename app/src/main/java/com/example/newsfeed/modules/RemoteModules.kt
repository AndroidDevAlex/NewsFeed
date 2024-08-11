package com.example.newsfeed.modules

import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.data.remote.api.ApiManager
import com.example.newsfeed.data.remote.api.habrApi.HabrServiceApi
import com.example.newsfeed.data.remote.api.redditApi.RedditServiceApi
import com.example.newsfeed.data.local.RoomDataSource
import com.example.newsfeed.data.remote.repository.BaseNewsRepository
import com.example.newsfeed.data.remote.repository.BookmarkRepositoryImpl
import com.example.newsfeed.data.remote.repository.BookmarkRepository
import com.example.newsfeed.data.remote.repository.DetailRepositoryImpl
import com.example.newsfeed.data.remote.repository.DetailRepository
import com.example.newsfeed.data.remote.repository.HabrNewsRepositoryImpl
import com.example.newsfeed.data.remote.repository.RedditNewsRepositoryImpl
import com.example.newsfeed.domain.useCase.bookmarkCase.BookmarkToggleUseCase
import com.example.newsfeed.domain.useCase.bookmarkCase.GetBookmarkNewsUseCase
import com.example.newsfeed.domain.useCase.homeCase.FetchNewsUseCase
import com.example.newsfeed.domain.useCase.homeCase.ToggleBookmarkUseCase
import com.example.newsfeed.data.remote.NewsFilterManager
import com.example.newsfeed.data.remote.NewsFilterManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
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
    fun provideDetailRepository(
        roomDataSource: RoomDataSource
    ): DetailRepository {
        return DetailRepositoryImpl(
            roomDataSource
        )
    }

    @Provides
    @Singleton
    fun provideFetchNewsUseCase(
        @Named("Habr") habrRepository: BaseNewsRepository,
        @Named("Reddit") redditRepository: BaseNewsRepository
    ): FetchNewsUseCase {
        return FetchNewsUseCase(habrRepository, redditRepository)
    }

    @Provides
    @Singleton
    fun provideToggleBookmarkUseCase(
        @Named("Habr") habrRepository: BaseNewsRepository,
        @Named("Reddit") redditRepository: BaseNewsRepository
    ): ToggleBookmarkUseCase {
        return ToggleBookmarkUseCase(habrRepository, redditRepository)
    }

    @Provides
    @Singleton
    @Named("Habr")
    fun provideHabrNewsRepository(
        apiManager: ApiManager,
        dataSource: RoomDataSource
    ): BaseNewsRepository {
        return HabrNewsRepositoryImpl(dataSource, apiManager)
    }

    @Provides
    @Singleton
    @Named("Reddit")
    fun provideRedditNewsRepository(
        apiManager: ApiManager,
        dataSource: RoomDataSource
    ): BaseNewsRepository {
        return RedditNewsRepositoryImpl(apiManager, dataSource)
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

    @Provides
    @Singleton
    fun provideNewsFilterManager(
        @Named("Habr") habrRepository: BaseNewsRepository,
        @Named("Reddit") redditRepository: BaseNewsRepository,
        @Named("IODispatcher") ioDispatcher: CoroutineDispatcher
    ): NewsFilterManager {
        val repositories = listOf(habrRepository, redditRepository)
        return NewsFilterManagerImpl(repositories, ioDispatcher)
    }
}