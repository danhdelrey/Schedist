package com.brighttorchstudio.schedist.core.helpers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.brighttorchstudio.schedist.MainActivity
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.core.common.NotificationPermissionState


object NotificationHelper {

    fun checkNotificationPermissionState(context: Context): NotificationPermissionState {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                    NotificationPermissionState.GRANTED_AND_ENABLED
                } else {
                    NotificationPermissionState.GRANTED_BUT_DISABLED
                }
            } else {
                NotificationPermissionState.DENIED
            }
        } else {
            // On pre-Tiramisu, only check if notifications are enabled:
            return if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                NotificationPermissionState.GRANTED_AND_ENABLED
            } else {
                NotificationPermissionState.GRANTED_BUT_DISABLED
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun pushNotification(
        id: Int,
        title: String?,
        description: String?,
        context: Context,
        verboseNotificationChannelName: String = "Verbose WorkManager Notifications",
        verboseNotificationChannelDescription: String = "Shows notifications whenever work starts",
        channelId: String = "VERBOSE_NOTIFICATION",
    ) {
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

        val pendingIntent: PendingIntent = createPendingIntent(context)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (checkNotificationPermissionState(context) == NotificationPermissionState.GRANTED_AND_ENABLED) {
            NotificationManagerCompat.from(context)
                .notify(id, builder.build())
        }

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