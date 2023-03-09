package com.aab.bot.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.aab.bot.activities.MainActivity
import com.aab.bot.data_models.Messages
import com.aab.bot.viewmodels.ChatActivityViewModel

class ChatActivityBroadcastReceiver: BroadcastReceiver() {

    companion object{

        const val TAG = "ChatBroadcastReceiver"

        const val BROD_CMD = "broadcast_command"

        const val BROD_CMD_RECEIVED_MESSAGE = 10
        const val BROD_CMD_ERROR = 90

        const val BROD_RECEIVED_MESSAGE = "broadcast_received_message"
        const val BROD_RECEIVED_USERNAME = "broadcast_received_username"

        const val CHAT_BROADCAST_RECEIVER_INTENT_ACTION = "com.aab.CHAT_BROADCAST_RECEIVER"
    }

    lateinit var chatActivityViewModel: ChatActivityViewModel

    fun setViewModel(viewModel: ChatActivityViewModel){
        this.chatActivityViewModel = viewModel
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.let {
            it.extras?.let { bundle ->
                handleCommand(bundle)
            }
        }
    }

    private fun handleCommand(bundle: Bundle){
        when (bundle.getInt(BROD_CMD)){
            BROD_CMD_RECEIVED_MESSAGE -> {
                val msg = bundle.getString(BROD_RECEIVED_MESSAGE) ?: "did not pass any message"
                val username = bundle.getString(BROD_RECEIVED_USERNAME) ?: MainActivity.DEFAULT_USER
                val message = Messages(msg, username)
                chatActivityViewModel.addMessage(message)
            }
            BROD_CMD_ERROR -> {
                Log.i(TAG, "ERROR")
            }
            else -> {
                Log.i(TAG, "Unknown command")
            }
        }
    }
}