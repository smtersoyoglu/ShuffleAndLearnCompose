package com.smtersoyoglu.shuffleandlearncompose.data.model

import com.google.gson.annotations.SerializedName

data class WordItem(
    val id: Int,
    val translation: String,
    val english: String,
    @SerializedName("image_url")
    val imageUrl: String,
    val sentence: String
)
