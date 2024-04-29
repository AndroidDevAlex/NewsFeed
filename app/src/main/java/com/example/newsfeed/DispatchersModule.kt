package com.example.newsfeed

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Named("IODispatcher")
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Named("DefaultDispatcher")
    fun provideDefDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }
}