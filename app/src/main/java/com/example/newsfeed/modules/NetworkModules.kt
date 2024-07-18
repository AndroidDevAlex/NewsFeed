package com.example.newsfeed.modules


import com.example.newsfeed.data.remote.api.habrApi.HabrServiceApi
import com.example.newsfeed.data.remote.api.redditApi.RedditServiceApi
import com.example.newsfeed.util.ConstantsUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModules {

    @Provides
    @Singleton
    fun provideRetrofitInstance(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("habr")
    fun provideHabrRetrofitInstance(): Retrofit {
        return provideRetrofitInstance(ConstantsUrl.HABR_NEWS_URL)
    }

    @Provides
    @Singleton
    @Named("reddit")
    fun provideRedditRetrofitInstance(): Retrofit {
        return provideRetrofitInstance(ConstantsUrl.REDDIT_NEWS_URL)
    }

    @Provides
    @Singleton
    fun provideRedditApiInstance(@Named("reddit") retrofit: Retrofit): RedditServiceApi {
        return retrofit.create(RedditServiceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHabrApiInstance(@Named("habr") retrofit: Retrofit): HabrServiceApi {
        return retrofit.create(HabrServiceApi::class.java)
    }
}