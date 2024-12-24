package com.smtersoyoglu.shuffleandlearncompose.screens.word_detail.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.EnglishTextColor2
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.FredokaRegular
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.FredokaSemiBold
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.SentenceTestColor2
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.TurkishTextColor2

@Composable
fun WordDetailsText(translation: String, english: String, sentence: String) {
    Text(
        text = translation,
        style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            color = TurkishTextColor2,
            fontFamily = FredokaRegular,
            fontSize = 30.sp
        )
    )
    Spacer(modifier = Modifier.height(4.dp))

    Text(
        text = english,
        style = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Bold,
            color = EnglishTextColor2,
            fontFamily = FredokaSemiBold,
            fontSize = 36.sp
        )
    )
    Spacer(modifier = Modifier.height(4.dp))

    Text(
        text = sentence,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.SemiBold,
            color = SentenceTestColor2,
            fontFamily = FredokaSemiBold,
            fontSize = 28.sp
        ),
        modifier = Modifier.padding(horizontal = 16.dp),
        textAlign = TextAlign.Center
    )
}
