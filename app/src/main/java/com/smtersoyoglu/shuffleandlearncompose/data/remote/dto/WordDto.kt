package com.smtersoyoglu.shuffleandlearncompose.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WordDto(
    @SerializedName("id") val id: Int,
    @SerializedName("translation") val translation: String = "",
    @SerializedName("english") val english: String = "",
    @SerializedName("image_url") val imageUrl: String = "",
    @SerializedName("sentence") val sentence: String = "",
    @SerializedName("is_learned") val isLearned: Boolean,
)



