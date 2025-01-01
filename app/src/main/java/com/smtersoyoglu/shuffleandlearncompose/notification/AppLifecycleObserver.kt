package com.smtersoyoglu.shuffleandlearncompose.notification

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit
import javax.inject.Inject

// AppLifecycleObserver -uygulamanın arka planda kalıp kalmadığını algıla
class AppLifecycleObserver @Inject constructor(
    private val context: Context
) : DefaultLifecycleObserver {

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)

        val workRequest: WorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(10, TimeUnit.SECONDS) // 10 saniye gecikme
            .build()

        // WorkManager ile iş zamanla
        WorkManager.getInstance(context).enqueue(workRequest)
    }
}
