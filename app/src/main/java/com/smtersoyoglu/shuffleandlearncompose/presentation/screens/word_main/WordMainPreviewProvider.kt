package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main.WordContract.UiState

class WordMainPreviewProvider : PreviewParameterProvider<UiState> {
    override val values: Sequence<UiState>
        get() = sequenceOf(
            UiState(
                isLoading = false,
                words = listOf(
                    WordItem(
                        id = 1,
                        translation = "Köpek",
                        english = "Dog",
                        url = "https://example.com/dog.png",
                        sentence = "",
                        isLearned = false
                    ),
                    WordItem(
                        id = 2,
                        translation = "Kedi",
                        english = "Cat",
                        url = "https://example.com/cat.png",
                        sentence = "",
                        isLearned = false
                    )
                ),
                shuffledWords = emptyList(),
                error = null
            ),
            UiState(
                isLoading = true,
                words = emptyList(),
                shuffledWords = emptyList(),
                error = null
            ),
            UiState(
                isLoading = false,
                words = emptyList(),
                shuffledWords = emptyList(),
                error = "Error fetching words"
            )
        )
}