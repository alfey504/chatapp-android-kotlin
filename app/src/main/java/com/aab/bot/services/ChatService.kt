package com.aab.bot.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.aab.bot.broadcast_receivers.ChatBroadcastReceiver
import com.aab.bot.notification.NotificationHelper


class ChatService : Service(){

    private lateinit var notificationService: NotificationHelper

    companion object{

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
            val bundle = Bundle()
            bundle.putInt(MSG_CMD, command)
            val intent = Intent(context, ChatService::class.java)
            intent.putExtras(bundle)
            context.startService(intent)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        notificationService = NotificationHelper(this)

        if(intent != null){
            val bundle: Bundle = if(intent.extras == null){
                val bundle = Bundle()
                bundle.putInt(MSG_CMD, CMD_ERROR_BUNDLE_NOT_FOUND)
                bundle
            }else{
                intent.extras!!
            }
            handleCommand(bundle)
        }
        return START_STICKY
    }

    private fun handleCommand(bundle: Bundle){
        when(bundle.getInt(MSG_CMD)) {
            CMD_SIMULATE_RECEIVE_MESSAGE -> {
                for (msg in messages){
                    broadcastMessage(msg)
                    //send notification
                    notificationService.displaySimpleNotification(msg, "New Message")
                }
            }
            CMD_STOP_SERVICE -> {
                notificationService.displaySimpleNotification("service has been stopped", "Service Stopped")
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

    private fun broadcastMessage(msg: String){
        val bundle = Bundle()
        bundle.putInt(ChatBroadcastReceiver.BROD_CMD, ChatBroadcastReceiver.BROD_CMD_RECEIVED_MESSAGE)
        bundle.putString(ChatBroadcastReceiver.BROD_RECEIVED_MESSAGE, msg)

        val chatBroadcastIntent = Intent(ChatBroadcastReceiver.CHAT_BROADCAST_RECEIVER_INTENT_ACTION)
        chatBroadcastIntent.putExtras(bundle)

        sendBroadcast(chatBroadcastIntent)
    }
}