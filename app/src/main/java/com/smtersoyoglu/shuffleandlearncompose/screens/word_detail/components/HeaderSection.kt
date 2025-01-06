package com.smtersoyoglu.shuffleandlearncompose.screens.word_detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.BackgroundColor
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.ButtonColor
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.FredokaBold
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.HeaderColor

@Composable
fun HeaderSection(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(34.dp)
            )
        }
        Text(
            text = buildAnnotatedString {
                append("Learn ")
                withStyle(
                    style = SpanStyle(
                        color = HeaderColor,
                        fontFamily = FredokaBold
                    )
                ) {
                    append("This ")
                }
                withStyle(
                    style = SpanStyle(
                        color = BackgroundColor,
                        fontFamily = FredokaBold
                    )
                ) {
                    append("Word")
                }
            },
            style = MaterialTheme.typography.headlineLarge.copy(
                color = ButtonDefaults.buttonColors(ButtonColor).containerColor,
                fontFamily = FredokaBold
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}