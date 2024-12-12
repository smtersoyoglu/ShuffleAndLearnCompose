package com.smtersoyoglu.shuffleandlearncompose.data.repository

import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem
import com.smtersoyoglu.shuffleandlearncompose.data.retrofit.WordService
import com.smtersoyoglu.shuffleandlearncompose.data.room.WordDao
import com.smtersoyoglu.shuffleandlearncompose.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WordRepository @Inject constructor(
    private val wordService: WordService, // Retrofit ile API'den veri alıyoruz
    private val wordDao: WordDao // Room ile veritabanı işlemleri
) {
    // Kelimeleri Retrofit'ten alıp Room veritabanına kaydediyoruz
    suspend fun getWords(): Resource<List<WordItem>> {
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

    // Öğrenilmemiş kelimeleri al
    fun getUnlearnedWordsFlow(): Flow<List<WordItem>> {
        return wordDao.getUnlearnedWords()
    }

    // Learned kelimeleri çekmek için bir metod
    fun getLearnedWords(): Flow<List<WordItem>> {
        return wordDao.getLearnedWords() // Bu query ile learned kelimeleri çekeceğiz
    }

    // Kelimeyi ID ile al
    suspend fun getWordById(id: Int): WordItem? {
        return wordDao.getWordById(id)
    }

    // Kelimeyi güncelle
    suspend fun updateWord(word: WordItem) {
        wordDao.updateWord(word) // Room'da güncelle
    }

    // Kelimeyi öğrenildi olarak işaretle
    suspend fun markWordAsLearned(word: WordItem) {
        wordDao.updateWord(word.copy(isLearned = true)) // Room'da güncelle
    }

}


