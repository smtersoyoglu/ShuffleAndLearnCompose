package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_main

import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem

data class WordState(
    val isLoading: Boolean = true,
    val words: List<WordItem> = emptyList(),
    val error: String? = null,
)