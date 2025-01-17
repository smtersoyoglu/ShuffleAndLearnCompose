package com.smtersoyoglu.shuffleandlearncompose.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WordDto(
    @SerializedName("id") val id: Int,
    @SerializedName("translation") val translation: String? = null,
    @SerializedName("english") val english: String? = null,
    @SerializedName("image_url") val url: String? = null,
    @SerializedName("sentence") val sentence: String? = null,
)



