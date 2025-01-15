package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.FredokaRegular
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.TurkishTextColor2

@Composable
fun ScoreDisplay(correctCount: Int, incorrectCount: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(77.dp)) {
        Text(
            text = "Doğru Sayısı: $correctCount",
            fontSize = 21.sp,
            fontFamily = FredokaRegular,
            color = TurkishTextColor2
        )
        Text(
            text = "Yanlış Sayısı: $incorrectCount",
            fontSize = 21.sp,
            fontFamily = FredokaRegular,
            color = TurkishTextColor2
        )
    }

}