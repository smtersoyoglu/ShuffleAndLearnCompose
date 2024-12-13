package com.smtersoyoglu.shuffleandlearncompose.screens.learned_words

import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem

data class LearnedWordsState(
    val isLoading: Boolean = true,
    val learnedWords: List<WordItem> = emptyList(),
    val error: String? = null
)