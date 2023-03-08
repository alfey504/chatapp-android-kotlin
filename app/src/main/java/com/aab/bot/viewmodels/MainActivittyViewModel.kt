package com.aab.bot.viewmodels

import android.os.Message
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainActivityViewModel: ViewModel() {

    private val _chatData = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf()
    }


    fun getChatData(): MutableLiveData<MutableList<String>> {
        return _chatData
    }

    fun addMessage(message: String){
        val messages = _chatData.value!!
        messages.add(message)
        _chatData.value = messages
    }
}