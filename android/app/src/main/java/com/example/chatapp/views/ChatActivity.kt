package com.example.chatapp.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.adapter.ChatAdapter
import com.example.chatapp.Model.Chat
import com.example.chatapp.ViewModel.ChatViewModel
import com.example.chatapp.databinding.ActivityMainBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var chatAdapter: ChatAdapter

    private var userName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra(USERNAME) ?: ""

        if (userName.isEmpty()) {
            finish()
        } else {
            viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

            chatAdapter = ChatAdapter()

            binding.rvChat.apply {
                layoutManager = LinearLayoutManager(this@ChatActivity)
                adapter = chatAdapter
            }

            binding.btnSend.setOnClickListener {
                val message = binding.etMsg.text.toString()
                if (message.isNotEmpty()) {
                    val chat = Chat(
                        username = userName,
                        text = message
                    )
                    viewModel.sendChatMessage(chat)
                    binding.etMsg.setText("")
                }
            }

            viewModel.getChatList().observe(this) { chatList ->
                chatAdapter.submitChat(chatList)
                binding.rvChat.scrollToPosition(chatList.size - 1)
            }
        }
    }

    override fun onDestroy() {
        viewModel.disconnectSocket()
        super.onDestroy()
    }

    companion object {
        const val USERNAME = "username"
    }
}
