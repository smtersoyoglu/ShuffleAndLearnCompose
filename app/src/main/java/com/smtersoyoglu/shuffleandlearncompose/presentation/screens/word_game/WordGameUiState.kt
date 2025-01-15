package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_game

import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem

data class WordGameUiState(
    val wordList: List<WordItem>? = emptyList(),
    val currentWord: WordItem? = null,
    val timer: Int = 60,
    val correctCount: Int = 0,
    val incorrectCount: Int = 0,
    val isGameOver: Boolean = false,
    val isCorrect: Boolean? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)


