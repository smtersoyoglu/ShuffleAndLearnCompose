package com.smtersoyoglu.shuffleandlearncompose.domain.model

data class WordItem(
    val id: Int,
    val translation: String,
    val english: String,
    val url: String,
    val sentence: String,
    val isLearned: Boolean,
)

