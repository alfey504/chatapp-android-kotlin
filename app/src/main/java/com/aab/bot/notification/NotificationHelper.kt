package com.aab.bot.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.aab.bot.R


class NotificationHelper(
    private val context: Context
) {

    companion object{

        const val CHANNEL_ID = "chat_notification"
        var NOTIFICATION_ID = 1

        fun initNotificationChannel(context: Context){

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "Chat Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }

    }

    fun displaySimpleNotification(message: String, title: String) {
        var builder = NotificationCompat.Builder(this.context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, builder.build())
        }

        NOTIFICATION_ID ++
    }
}