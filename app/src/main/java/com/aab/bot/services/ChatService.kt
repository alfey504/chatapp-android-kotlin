package com.aab.bot.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.aab.bot.activities.MainActivity
import com.aab.bot.broadcast_receivers.ChatActivityBroadcastReceiver
import com.aab.bot.notification.NotificationHelper


class ChatService : Service(){

    private lateinit var notificationService: NotificationHelper

    companion object{

        var USERNAME = MainActivity.DEFAULT_USER

        // Tag for logging
        const val TAG = "ChatService"

        // received messages
        private var messages = arrayListOf<String>("Hello Abraham", "How are you?", "Good Bye Abraham!")

        //commands
        const val CMD_SIMULATE_RECEIVE_MESSAGE = 10
        const val CMD_STOP_SERVICE = 20
        const val CMD_ERROR_BUNDLE_NOT_FOUND = 90

        // keys
        const val MSG_CMD = "msg_cmd"
        const val MSG_TEXT = "msg_text"

        // start the chat Service with a command
        fun startChatServiceWithCommand(context: Context, command: Int){
            val bundle = Bundle().apply {
                putInt(MSG_CMD, command)
            }
            val intent = Intent(context, ChatService::class.java).apply {
                putExtras(bundle)
            }

            context.startService(intent)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        notificationService = NotificationHelper(this)

        intent?.let {
            it.extras?.let { bundle ->
                handleCommand(bundle)
            }
        }
        return START_STICKY
    }

    private fun handleCommand(bundle: Bundle){
        when(bundle.getInt(MSG_CMD)) {
            CMD_SIMULATE_RECEIVE_MESSAGE -> {
                for (msg in messages){
                    broadcastMessage(msg, getUsername())
                    //send notification
                    notificationService.displayCustomNotification(msg, username = getUsername())
                }
            }
            CMD_STOP_SERVICE -> {
                notificationService.displaySimpleNotification("ChatBot Stopped: 98", "Service Stopped")
                stopSelf()
            }
            CMD_ERROR_BUNDLE_NOT_FOUND -> {
                Log.i(TAG, "Error did not receive a bundle in intent")
            }
            else -> {
                Log.i(TAG, "Unknown Command")
            }
        }
    }

    private fun stopService(){
        stopSelf()
    }

    private fun broadcastMessage(msg: String, username: String){
        val bundle = Bundle().apply {
            putInt(ChatActivityBroadcastReceiver.BROD_CMD, ChatActivityBroadcastReceiver.BROD_CMD_RECEIVED_MESSAGE)
            putString(ChatActivityBroadcastReceiver.BROD_RECEIVED_MESSAGE, msg)
            putString(ChatActivityBroadcastReceiver.BROD_RECEIVED_USERNAME, username)
        }

        val chatBroadcastIntent = Intent(ChatActivityBroadcastReceiver.CHAT_BROADCAST_RECEIVER_INTENT_ACTION).apply {
            putExtras(bundle)
        }
        sendBroadcast(chatBroadcastIntent)
    }

    private fun setUser(username: String){
        USERNAME = username
    }

    private fun getUsername(): String {
        val sharedPrefs =
            getSharedPreferences(MainActivity.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)
        return sharedPrefs.getString(MainActivity.USERNAME_KEY, MainActivity.DEFAULT_USER) ?: MainActivity.DEFAULT_USER
    }
}