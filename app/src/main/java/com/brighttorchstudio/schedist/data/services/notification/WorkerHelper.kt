package com.brighttorchstudio.schedist.data.services.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.brighttorchstudio.schedist.MainActivity
import com.brighttorchstudio.schedist.R

object WorkerHelper {

    @SuppressLint("MissingPermission")
    fun makeReminderNotification(
        notificationTitle: String?,
        notificationMessage: String?,
        context: Context,
        verboseNotificationChannelName: String = "Verbose WorkManager Notifications",
        verboseNotificationChannelDescription: String = "Shows notifications whenever work starts",
        channelId: String = "VERBOSE_NOTIFICATION",
        notificationId: Int = 1
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                channelId,
                verboseNotificationChannelName,
                importance
            )
            channel.description = verboseNotificationChannelDescription

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }

        val pendingIntent: PendingIntent = createPendingIntent(context)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(notificationTitle)
            .setContentText(notificationMessage)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        NotificationManagerCompat.from(context).notify(notificationId, builder.build())
    }

    private fun createPendingIntent(
        appContext: Context,
        requestCode: Int = 0,
    ): PendingIntent {

        val intent = Intent(appContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // Flag to detect unsafe launches of intents for Android 12 and higher
        // to improve platform security
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        return PendingIntent.getActivity(
            appContext,
            requestCode,
            intent,
            flags
        )
    }
}