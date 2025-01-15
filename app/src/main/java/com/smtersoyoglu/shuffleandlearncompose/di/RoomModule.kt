package com.smtersoyoglu.shuffleandlearncompose.di

import android.app.Application
import androidx.room.Room
import com.smtersoyoglu.shuffleandlearncompose.data.local.WordDatabase
import com.smtersoyoglu.shuffleandlearncompose.common.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideWordDatabase(app: Application): WordDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            WordDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideWordDao(wordDatabase: WordDatabase) = wordDatabase.wordDao()

}