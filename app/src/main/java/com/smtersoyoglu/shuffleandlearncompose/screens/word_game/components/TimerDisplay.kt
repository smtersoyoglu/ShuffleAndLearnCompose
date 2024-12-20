package com.smtersoyoglu.shuffleandlearncompose.screens.word_game.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.FredokaSemiBold

@Composable
fun TimerDisplay(timeRemaining: Int) {
    Text(
        text = "Kalan Süre: $timeRemaining",
        fontSize = 24.sp,
        fontFamily = FredokaSemiBold
    )
}