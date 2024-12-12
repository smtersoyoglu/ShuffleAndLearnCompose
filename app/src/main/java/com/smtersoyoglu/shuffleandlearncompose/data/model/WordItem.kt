package com.smtersoyoglu.shuffleandlearncompose.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "words")
data class WordItem(
    @PrimaryKey val id: Int,
    val translation: String = "",
    val english: String = "",
    @SerializedName("image_url")
    val imageUrl: String = "",
    val sentence: String = "",
    var isLearned: Boolean = false
)
