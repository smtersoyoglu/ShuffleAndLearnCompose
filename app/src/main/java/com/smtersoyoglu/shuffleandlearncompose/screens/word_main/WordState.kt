package com.smtersoyoglu.shuffleandlearncompose.screens.word_main

import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem

// UI durumunu tutmak için State sınıfı
data class WordState(
    val isLoading: Boolean = true,
    val words: List<WordItem> = emptyList(),
    val error: String? = null
)