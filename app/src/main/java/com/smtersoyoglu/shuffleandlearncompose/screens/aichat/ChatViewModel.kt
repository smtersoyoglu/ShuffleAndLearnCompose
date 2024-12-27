package com.smtersoyoglu.shuffleandlearncompose.screens.aichat

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.smtersoyoglu.shuffleandlearncompose.data.repository.WordRepository
import com.smtersoyoglu.shuffleandlearncompose.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val wordRepository: WordRepository,
) : ViewModel() {

    val messageList by lazy { mutableStateListOf<MessageModel>() }

    private val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = Constants.apiKey
    )

    fun sendMessage(message: String) {
        viewModelScope.launch {
            try {
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role) { text(it.message) }
                    }.toList()
                )

                messageList.add(MessageModel(message, "user"))
                messageList.add(MessageModel("Typing....", "model"))

                val response = chat.sendMessage(message)
                messageList.removeAt(messageList.lastIndex)
                messageList.add(MessageModel(response.text.toString(), "model"))

            } catch (e: Exception) {
                messageList.removeAt(messageList.lastIndex)
                messageList.add(MessageModel("Error : " + e.message.toString(), "model"))
            }
        }
    }
}