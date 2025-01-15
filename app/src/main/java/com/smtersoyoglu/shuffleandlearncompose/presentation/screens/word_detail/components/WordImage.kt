package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun WordImage(imageUrl: String, description: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = description,
        modifier = Modifier
            .height(240.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        contentScale = ContentScale.Fit
    )
}
