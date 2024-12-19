package com.smtersoyoglu.shuffleandlearncompose.screens.word_game

import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem

data class WordGameUiState(
    val currentWord: WordItem? = null,
    val timer: Int = 60,
    val correctCount: Int = 0,
    val incorrectCount: Int = 0,
    val wordList: List<WordItem> = emptyList(),
    val isGameOver: Boolean = false,
)

