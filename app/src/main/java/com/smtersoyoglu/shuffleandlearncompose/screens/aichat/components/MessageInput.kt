package com.smtersoyoglu.shuffleandlearncompose.screens.aichat.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.focusedBorderColor
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.focusedLabelColor
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.unfocusedBorderColor
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.unfocusedLabelColor

@Composable
fun MessageInput(onMessageSend: (String) -> Unit) {

    var message by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.padding(start = 10.dp, end = 8.dp, bottom = 8.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = message,
            onValueChange = {
                message = it
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                focusedBorderColor = focusedBorderColor,
                unfocusedBorderColor = unfocusedBorderColor,
                focusedLabelColor = focusedLabelColor,
                unfocusedLabelColor = unfocusedLabelColor,
            )
        )
        IconButton(onClick = {
            if (message.isNotEmpty()) {
                onMessageSend(message)
                message = ""
            }
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send",
                tint = Color.White
            )
        }
    }
}
