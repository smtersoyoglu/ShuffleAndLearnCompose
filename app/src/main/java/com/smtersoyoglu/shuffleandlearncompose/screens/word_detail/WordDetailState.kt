package com.smtersoyoglu.shuffleandlearncompose.screens.word_detail

import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem

data class WordDetailState(
    val isLoading: Boolean = true,
    val word: WordItem? = null,
    val error: String? = null
)
