package com.smtersoyoglu.shuffleandlearncompose.screens.aichat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.BackgroundColor
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.FredokaSemiBold

@Composable
fun AppHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundColor)
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Chat Bot",
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = FredokaSemiBold
        )
    }
}