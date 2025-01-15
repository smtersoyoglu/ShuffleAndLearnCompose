package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.smtersoyoglu.shuffleandlearncompose.R

@Composable
fun LottieEmptyState(
    modifier: Modifier = Modifier,
    lottieResId: Int,
    animationSize: Modifier = Modifier.fillMaxSize()
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieResId))
    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = animationSize.then(modifier)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLottieEmptyState() {
    LottieEmptyState(
        lottieResId = R.raw.anim_empty
    )
}