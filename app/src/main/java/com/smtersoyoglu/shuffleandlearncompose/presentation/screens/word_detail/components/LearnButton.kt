package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.ButtonColor
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.FredokaSemiBold
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.LearnedButtonColor

@Composable
fun LearnButton(isLearned: Boolean, onLearned: () -> Unit) {
    Button(
        onClick = { onLearned() },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isLearned) LearnedButtonColor else ButtonColor
        ),
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(vertical = 18.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = if (isLearned) "Learned" else "Learn",
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            fontFamily = FredokaSemiBold,
            modifier = Modifier.padding(8.dp)
        )
    }
}
