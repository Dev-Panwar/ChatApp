package com.example.chatapp.ViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.Model.Chat
import com.example.chatapp.SocketHandler

class ChatViewModel : ViewModel() {
    private val socketHandler = SocketHandler()

    fun getChatList(): LiveData<List<Chat>> = socketHandler.chatList

    fun sendChatMessage(chat: Chat) {
        socketHandler.emitChat(chat)
    }

    fun disconnectSocket() {
        socketHandler.disconnectSocket()
    }
}
