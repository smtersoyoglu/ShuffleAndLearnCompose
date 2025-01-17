package com.smtersoyoglu.shuffleandlearncompose.domain.model

import com.google.gson.annotations.SerializedName

data class WordItem(
    val id: Int,
    val translation: String,
    val english: String,
    val url: String,
    val sentence: String,
    val isLearned: Boolean,
)

