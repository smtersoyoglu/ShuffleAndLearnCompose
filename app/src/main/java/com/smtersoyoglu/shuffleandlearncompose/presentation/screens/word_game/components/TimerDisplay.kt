package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.FredokaSemiBold
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.TurkishTextColor2

@Composable
fun TimerDisplay(timeRemaining: Int) {
    Text(
        text = "Kalan Süre: $timeRemaining",
        fontSize = 24.sp,
        fontFamily = FredokaSemiBold,
        color = TurkishTextColor2
    )
}