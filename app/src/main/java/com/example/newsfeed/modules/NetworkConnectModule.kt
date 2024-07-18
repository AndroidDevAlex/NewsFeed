package com.example.newsfeed.modules

import android.content.Context
import android.net.ConnectivityManager
import com.example.newsfeed.internetConection.NetworkConnectivityObserver
import com.example.newsfeed.internetConection.NetworkStateObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkConnectModule {

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideNetworkStateObserver(
        connectivityManager: ConnectivityManager,
        @Named("MainDispatcher") mainDispatcher: CoroutineDispatcher
    ): NetworkStateObserver {
        return NetworkConnectivityObserver(connectivityManager, mainDispatcher)
    }
}