package com.smtersoyoglu.shuffleandlearncompose.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem

@Dao
interface WordDao {
    @Query("SELECT * FROM words WHERE isLearned = 0")
    suspend fun getUnlearnedWords(): List<WordItem> // Öğrenilmemiş kelimeleri getir

    @Query("SELECT * FROM words WHERE isLearned = 1")
    suspend fun getLearnedWords(): List<WordItem>  // Öğrenilmiş kelimeleri getir

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<WordItem>) // Kelimeleri ekle

    @Update
    suspend fun updateWord(word: WordItem) // Kelime güncelle

}