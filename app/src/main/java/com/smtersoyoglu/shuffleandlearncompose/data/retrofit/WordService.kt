package com.smtersoyoglu.shuffleandlearncompose.data.retrofit

import com.smtersoyoglu.shuffleandlearncompose.data.model.Word
import retrofit2.http.GET

interface WordService {
    @GET("words.json")
    suspend fun getWords() : Word
}