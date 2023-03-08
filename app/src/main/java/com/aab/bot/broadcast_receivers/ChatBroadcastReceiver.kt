package com.aab.bot.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.aab.bot.viewmodels.MainActivityViewModel

class ChatBroadcastReceiver: BroadcastReceiver() {

    companion object{

        const val TAG = "ChatBroadcastReceiver"

        const val BROD_CMD = "broadcast_command"

        const val BROD_CMD_RECEIVED_MESSAGE = 10
        const val BROD_CMD_ERROR = 90

        const val BROD_RECEIVED_MESSAGE = "broadcast_received_message"

        const val CHAT_BROADCAST_RECEIVER_INTENT_ACTION = "com.aab.CHAT_BROADCAST_RECEIVER"
    }

    lateinit var mainActivityViewModel: MainActivityViewModel

    fun setViewModel(viewModel: MainActivityViewModel){
        this.mainActivityViewModel = viewModel
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent!= null){
            val bundle = if(intent.extras != null){
                intent.extras!!
            }else{
                val bundle = Bundle()
                bundle.putInt(BROD_CMD, BROD_CMD_ERROR)
                bundle
            }
            handleCommand(bundle)
        }
    }

    private fun handleCommand(bundle: Bundle){
        when (bundle.getInt(BROD_CMD)){
            BROD_CMD_RECEIVED_MESSAGE -> {
                mainActivityViewModel.addMessage(bundle.getString(BROD_RECEIVED_MESSAGE) ?: "did not pass any message")
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