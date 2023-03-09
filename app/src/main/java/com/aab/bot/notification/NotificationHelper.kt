package com.aab.bot.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.aab.bot.R
import java.text.SimpleDateFormat
import java.util.*


class NotificationHelper(
    private val context: Context
) {

    companion object{

        const val TAG = "NotificationHelper"

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

    fun displayCustomNotification(
        message: String,
        username: String
    ) {


        val notificationLayout = RemoteViews(context.packageName, R.layout.custom_notification)
        notificationLayout.setImageViewResource(R.id.notification_image, R.drawable.avatar_png)
        notificationLayout.setTextViewText(R.id.notification_name, username)
        notificationLayout.setTextViewText(R.id.notification_message, message)
        notificationLayout.setTextViewText(R.id.notification_date, getCurrentDateTime())

        try{
            var builder = NotificationCompat.Builder(this.context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContent(notificationLayout)

            with(NotificationManagerCompat.from(context)) {
                notify(NOTIFICATION_ID, builder.build())
            }

            NOTIFICATION_ID ++
        }catch(e: Exception){
            Log.e(TAG, e.stackTraceToString())
        }

    }

    private fun getCurrentDateTime(): String{
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm a")
        return dateFormat.format(cal.time)
    }
}