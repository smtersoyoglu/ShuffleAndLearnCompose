package com.smtersoyoglu.shuffleandlearncompose.screens.word_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.smtersoyoglu.shuffleandlearncompose.ui.theme.ButtonColor


@Composable
fun ColorBlock() {
    Box(
        modifier = Modifier.fillMaxSize() // Tüm alanı kaplar
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.5f)
                .align(Alignment.TopEnd) // Sağ üst köşeye hizalar
                .background(ButtonColor)
        )
    }
}