package com.smtersoyoglu.shuffleandlearncompose.data.remote

import com.smtersoyoglu.shuffleandlearncompose.data.remote.dto.WordDto
import retrofit2.http.GET

interface WordService {
    @GET("words.json")
    suspend fun getWords(): List<WordDto>
}