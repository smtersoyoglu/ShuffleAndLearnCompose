package com.smtersoyoglu.shuffleandlearncompose.data.repository

import android.util.Log
import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.data.retrofit.WordService
import com.smtersoyoglu.shuffleandlearncompose.data.room.WordDao
import com.smtersoyoglu.shuffleandlearncompose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class WordRepository @Inject constructor(
    private val wordService: WordService, // Retrofit ile API'den veri alıyoruz
    private val wordDao: WordDao // Room ile veritabanı işlemleri
) {
    // Kelimeleri Retrofit'ten alıp Room veritabanına kaydediyoruz
    suspend fun fetchAndSaveWords(): Resource<List<WordItem>> {
        return try {
            val response = wordService.getWords() // API'den kelimeleri al
            val cleanedWords = response.map { wordItem ->
                wordItem.copy(
                    translation = wordItem.translation ?: "",
                    english = wordItem.english ?: "",
                    imageUrl = wordItem.imageUrl ?: "",
                    sentence = wordItem.sentence ?: ""
                )
            }
            wordDao.insertWords(cleanedWords) // Room'a kaydet
            Resource.Success(cleanedWords)
        } catch (e: Exception) {
            Resource.Error("Error fetching words: ${e.message}")
        }
    }

    // Tüm kelimeleri al
    fun getAllWords(): Flow<List<WordItem>> {
        return wordDao.getAllWords()
    }

    // Öğrenilmemiş kelimeleri al
    fun getUnlearnedWords(): Flow<List<WordItem>> {
        return wordDao.getUnlearnedWords()
    }

    // Learned kelimeleri çekmek için bir metod
    fun getLearnedWords(): Flow<List<WordItem>> {
        return wordDao.getLearnedWords().onEach { learnedWords ->
            Log.d("WordRepository", "Flow Fetched Learned Words: $learnedWords")
        }
    }

    // Kelimeyi ID ile al, ID'ye göre bir kelimeyi döndürür.
    suspend fun getWordById(id: Int): WordItem? {
        return wordDao.getWordById(id)
    }

    suspend fun markWordAsLearned(word: WordItem) {
        wordDao.updateLearnedStatus(word.id, true)
        val updatedWord = wordDao.getWordById(word.id)
        Log.d("WordRepository", "Updated Word in DB: $updatedWord")
    }


    suspend fun markWordAsUnlearned(word: WordItem) {
        wordDao.updateLearnedStatus(word.id, false)
    }

}


