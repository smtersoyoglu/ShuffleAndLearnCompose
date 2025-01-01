package com.smtersoyoglu.shuffleandlearncompose.di

import android.app.Application
import android.content.Context
import com.smtersoyoglu.shuffleandlearncompose.notification.AppLifecycleObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Hilt Module for AppLifecycleObserver
@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideAppLifecycleObserver(
        context: Context
    ): AppLifecycleObserver {
        return AppLifecycleObserver(context)
    }
}
