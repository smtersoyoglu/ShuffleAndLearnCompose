package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.aichat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.aichat.MessageModel
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.ColorModelMessage
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.ColorUserMessage
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.FredokaRegular

@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel = messageModel.role == "model"

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .align(if (isModel) Alignment.BottomStart else Alignment.BottomEnd)
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(if (isModel) ColorModelMessage else ColorUserMessage)
                    .padding(16.dp)
            ) {
                Text(
                    text = messageModel.message,
                    fontWeight = FontWeight.W500,
                    fontFamily = FredokaRegular,
                    color = if (isModel) Color.White else Color.Black // Kullanıcı mesajı için siyah yazı
                )
            }
        }
    }
}