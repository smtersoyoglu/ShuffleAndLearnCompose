package com.smtersoyoglu.shuffleandlearncompose.screens.word_game

import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem

data class WordGameUiState(
    val wordList: List<WordItem> = emptyList(),
    val currentWord: WordItem? = null,
    val timer: Int = 20,
    val correctCount: Int = 0,
    val incorrectCount: Int = 0,
    val isGameOver: Boolean = false,
    val isCorrect: Boolean? = null,
)

