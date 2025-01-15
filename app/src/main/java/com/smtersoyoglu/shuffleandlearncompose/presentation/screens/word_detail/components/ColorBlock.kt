package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smtersoyoglu.shuffleandlearncompose.presentation.theme.ButtonColor

@Composable
fun ColorBlock() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(400.dp)
                .align(Alignment.TopEnd) // Sağ üst köşeye hizalama
                .background(ButtonColor)
        )
    }
}