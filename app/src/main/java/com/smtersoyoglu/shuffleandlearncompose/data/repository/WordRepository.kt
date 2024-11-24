package com.smtersoyoglu.shuffleandlearncompose.data.repository

import com.smtersoyoglu.shuffleandlearncompose.data.model.Word
import com.smtersoyoglu.shuffleandlearncompose.data.retrofit.WordService
import com.smtersoyoglu.shuffleandlearncompose.util.Resource
import javax.inject.Inject

class WordRepository @Inject constructor(private val wordService: WordService) {

    suspend fun getWords(): Resource<List<Word>> {
        return try {
            val response = wordService.getWords()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Error fetching words: ${e.message}")
        }
    }
}
