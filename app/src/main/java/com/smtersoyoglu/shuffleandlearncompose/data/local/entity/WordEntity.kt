package com.smtersoyoglu.shuffleandlearncompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordEntity(
    @PrimaryKey
    val id: Int,
    val translation: String? = null,
    val english: String? = null,
    val url: String? = null,
    val sentence: String? = null,
    var isLearned: Boolean = false,
)

