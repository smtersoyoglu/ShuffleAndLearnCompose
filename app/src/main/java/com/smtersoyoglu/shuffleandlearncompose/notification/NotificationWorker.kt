package com.smtersoyoglu.shuffleandlearncompose.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.smtersoyoglu.shuffleandlearncompose.MainActivity
import com.smtersoyoglu.shuffleandlearncompose.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlin.random.Random

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val notificationManager =
            applicationContext.getSystemService(NotificationManager::class.java)

        // Intent ve PendingIntent oluşturma
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Bildirimi oluşturma
        val notification = NotificationCompat.Builder(applicationContext, "note_notification")
            .setContentTitle("Bu kadar ara yeter!")
            .setContentText("Öğrenmeye geri dön ve yeni kelimeler keşfet!")
            .setSmallIcon(R.drawable.duck_card)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        // Bildirimi göster
        notificationManager?.notify(Random.nextInt(), notification)

        return Result.success()
    }
}