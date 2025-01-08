package com.smtersoyoglu.shuffleandlearncompose.di

import android.app.Application
import androidx.work.WorkManager
import com.smtersoyoglu.shuffleandlearncompose.notification.AppLifecycleObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    // WorkManager örneğini sağlıyoruz
    @Provides
    @Singleton
    fun provideWorkManager(application: Application): WorkManager {
        return WorkManager.getInstance(application)
    }

    // AppLifecycleObserver için bağımlılıkları sağlıyoruz
    @Provides
    @Singleton
    fun provideAppLifecycleObserver(workManager: WorkManager): AppLifecycleObserver {
        return AppLifecycleObserver(workManager)
    }
}
