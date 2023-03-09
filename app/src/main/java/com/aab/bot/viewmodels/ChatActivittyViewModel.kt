package com.aab.bot.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aab.bot.data_models.Messages


class ChatActivityViewModel: ViewModel() {

    private val _chatData = MutableLiveData<MutableList<Messages>>().apply {
        value = mutableListOf()
    }


    fun getChatData(): MutableLiveData<MutableList<Messages>> {
        return _chatData
    }

    fun addMessage(message: Messages){
        val messages = _chatData.value!!
        messages.add(message)
        _chatData.value = messages
    }
}