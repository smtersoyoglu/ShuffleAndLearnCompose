package com.smtersoyoglu.shuffleandlearncompose.notification

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AppLifecycleObserver @Inject constructor(
    private val workManager: WorkManager
) : DefaultLifecycleObserver {

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        // Uygulama arka plana alındığında bir iş planlıyoruz
        val workRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(15, TimeUnit.SECONDS) // 15 saniye gecikme
            .build()

        // WorkManager ile işi planlama (aynı iş zaten varsa yenisiyle değiştir)
        workManager.enqueueUniqueWork(
            "notification_work",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
        Log.d("AppLifecycleObserver", "Bildirim planlandı")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        // Uygulama tekrar öne alındığında, bildirim işi iptal ediliyor
        workManager.cancelUniqueWork("notification_work")
        Log.d("AppLifecycleObserver", "Bildirim iptal edildi")
    }
}
