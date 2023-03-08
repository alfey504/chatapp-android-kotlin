package com.aab.bot.activities

import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aab.bot.adapters.ChatViewAdapter
import com.aab.bot.databinding.ActivityMainBinding
import com.aab.bot.broadcast_receivers.ChatBroadcastReceiver
import com.aab.bot.services.ChatService
import com.aab.bot.viewmodels.MainActivityViewModel


class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "MainActivity"
    }

    private lateinit var viewModel: MainActivityViewModel
    private  lateinit var  binding: ActivityMainBinding
    private  lateinit var chatBroadcastReceiver: ChatBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        binding.mainActivityReceiveMessageButton.setOnClickListener {
            simulateReceiveMessage()
        }

        binding.mainActivityStopServiceButton.setOnClickListener {
           stopService()
        }

        binding.mainActivityChatListView.layoutManager = LinearLayoutManager(this)

        val adapter = ChatViewAdapter(mutableListOf())
        binding.mainActivityChatListView.adapter = adapter

        setRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        chatBroadcastReceiver = ChatBroadcastReceiver()
        val intentFilter = IntentFilter(ChatBroadcastReceiver.CHAT_BROADCAST_RECEIVER_INTENT_ACTION)
        registerReceiver(chatBroadcastReceiver, intentFilter)
        chatBroadcastReceiver.setViewModel(viewModel)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(chatBroadcastReceiver)
    }

    private fun setRecyclerView()
    {
        viewModel.getChatData().observe(this){
            binding.mainActivityChatListView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = ChatViewAdapter(it)
            }
        }
    }


    private fun simulateReceiveMessage(){
        ChatService.startChatServiceWithCommand(this, ChatService.CMD_SIMULATE_RECEIVE_MESSAGE)
    }

    private fun stopService(){
        ChatService.startChatServiceWithCommand(this, ChatService.CMD_STOP_SERVICE)
    }

}