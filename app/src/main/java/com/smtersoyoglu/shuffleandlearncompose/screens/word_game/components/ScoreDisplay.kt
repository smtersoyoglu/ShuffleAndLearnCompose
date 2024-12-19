package com.smtersoyoglu.shuffleandlearncompose.screens.word_game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.FredokaRegular

@Composable
fun ScoreDisplay(correctCount: Int, incorrectCount: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(99.dp)) {
        Text(
            text = "Doğru Sayısı: $correctCount",
            fontSize = 24.sp,
            fontFamily = FredokaRegular
        )
        Text(
            text = "Yanlış Sayısı: $incorrectCount",
            fontSize = 24.sp,
            fontFamily = FredokaRegular
        )
    }

}