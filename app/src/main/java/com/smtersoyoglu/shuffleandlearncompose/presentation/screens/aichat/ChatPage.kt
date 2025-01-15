package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.aichat

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.aichat.components.AppHeader
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.aichat.components.MessageInput
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.aichat.components.MessageList

@Composable
fun ChatPage(modifier: Modifier = Modifier,
             viewModel: ChatViewModel = hiltViewModel(),
             navController: NavController
) {
    Column(modifier = modifier) {
        AppHeader()
        MessageList(modifier = Modifier.weight(1f), messageList = viewModel.messageList)
        MessageInput(onMessageSend = {
            viewModel.sendMessage(it)
        })
    }
}

