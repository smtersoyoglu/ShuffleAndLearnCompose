package com.smtersoyoglu.shuffleandlearncompose.presentation.screens.learned_words

import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem

data class LearnedWordsState(
    val isLoading: Boolean = true,
    val learnedWords: List<WordItem> = emptyList(),
    val error: String? = null
)