package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.word_detail

import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem

data class WordDetailState(
    val isLoading: Boolean = true,
    val word: WordItem? = null,
    val error: String? = null
)
