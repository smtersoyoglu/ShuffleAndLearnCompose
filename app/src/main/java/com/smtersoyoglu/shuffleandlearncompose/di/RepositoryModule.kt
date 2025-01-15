package com.smtersoyoglu.shuffleandlearncompose.di

import com.smtersoyoglu.shuffleandlearncompose.data.repository.WordRepositoryImpl
import com.smtersoyoglu.shuffleandlearncompose.domain.repository.WordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWordRepository(
        wordRepositoryImpl: WordRepositoryImpl,
    ): WordRepository
}


