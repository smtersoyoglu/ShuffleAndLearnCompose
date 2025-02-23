package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game.GameContract.UiState

class WordGamePreviewProvider : PreviewParameterProvider<UiState> {
    override val values: Sequence<UiState>
        get() = sequenceOf(
            UiState(
                isLoading = true,
                error = "Kelime listesi yüklenemedi"
            ),

            UiState(
                wordList = listOf(
                    WordItem(
                        id = 1, translation = "Köpek", english = "Dog", url = "https://example.com/dog.png", sentence = "", isLearned = false
                    ),
                    WordItem(
                        id = 2, translation = "Kedi", english = "Cat", url = "https://example.com/cat.png", sentence = "", isLearned = false
                    )
                ),
                currentWord = WordItem(
                    id = 1, translation = "Köpek", english = "Dog", url = "https://example.com/dog.png", sentence = "", isLearned = false
                ),
                timer = 60,
                correctCount = 0,
                incorrectCount = 0,
                isGameOver = false,
                isCorrect = false,
                isLoading = false,
                error = null
            ),

            UiState(
                wordList = emptyList(),
                currentWord = null,
                timer = 0,
                correctCount = 5,
                incorrectCount = 2,
                isGameOver = true,
                isCorrect = null,
                isLoading = false,
                error = null
            )
        )
}