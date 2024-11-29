package com.smtersoyoglu.shuffleandlearncompose.di

import com.smtersoyoglu.shuffleandlearncompose.data.retrofit.WordService
import com.smtersoyoglu.shuffleandlearncompose.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWordService(retrofit: Retrofit) : WordService {
        return retrofit.create(WordService::class.java)
    }
}