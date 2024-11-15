package com.smtersoyoglu.shuffleandlearncompose.di

import com.smtersoyoglu.shuffleandlearncompose.data.retrofit.WordService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/smtersoyoglu/WordsJson/main/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWordService(retrofit: Retrofit) : WordService {
        return retrofit.create(WordService::class.java)
    }
}