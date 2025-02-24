package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words.LearnedContract.UiState

class LearnedWordsPreviewProvider : PreviewParameterProvider<UiState> {
    override val values: Sequence<UiState>
        get() = sequenceOf(
            UiState(
                isLoading = false,
                learnedWords = listOf(
                    WordItem(
                        id = 1, translation = "Köpek", english = "Dog", url = "https://example.com/dog.png", sentence = "", isLearned = false
                    ),
                    WordItem(
                        id = 2, translation = "Elma", english = "Apple", url = "https://example.com/apple.png", sentence = "", isLearned = false
                    )
                ),
                error = null
            ),
            UiState(
                isLoading = true,
                learnedWords = emptyList(),
            )
        )
}