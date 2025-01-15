package com.smtersoyoglu.shuffleandlearncompose.domain.repository

import com.smtersoyoglu.shuffleandlearncompose.domain.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.common.Resource
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    suspend fun fetchAndSaveWords(): Resource<List<WordItem>>
    fun getAllWords(): Flow<Resource<List<WordItem>>>
    fun getUnlearnedWords(): Flow<Resource<List<WordItem>>>
    fun getLearnedWords(): Flow<Resource<List<WordItem>>>
    suspend fun getWordById(id: Int): Resource<WordItem?>
    suspend fun markWordAsLearned(word: WordItem)
    suspend fun markWordAsUnlearned(word: WordItem)
}