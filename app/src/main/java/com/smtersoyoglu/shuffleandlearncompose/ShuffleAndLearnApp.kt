package com.smtersoyoglu.shuffleandlearncompose

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ShuffleAndLearnApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
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
            .setMinimumLoggingLevel(android.util.Log.INFO) // Özel ayarlar ekleyebilirsiniz
            .build()
}
