package com.aab.bot

import android.app.Application
import com.aab.bot.notification.NotificationHelper

class AppInit: Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationHelper.initNotificationChannel(this)
    }
}