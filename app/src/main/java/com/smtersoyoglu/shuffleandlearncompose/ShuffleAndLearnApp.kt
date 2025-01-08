package com.smtersoyoglu.shuffleandlearncompose

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.Configuration
import com.smtersoyoglu.shuffleandlearncompose.notification.AppLifecycleObserver
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ShuffleAndLearnApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var lifecycleObserver: AppLifecycleObserver

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        // Lifecycle observer'ı uygulama yaşam döngüsüne bağla
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)
    }

    // Notification Channel oluşturma
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val existingChannel = notificationManager.getNotificationChannel("note_notification")
            if (existingChannel == null) {
                val notificationChannel = NotificationChannel(
                    "note_notification",
                    "Note",
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }
    }

    // HiltWorkerFactory'yi kullanarak WorkManager yapılandırması
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
