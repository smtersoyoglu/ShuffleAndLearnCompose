package com.smtersoyoglu.shuffleandlearncompose.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<WordItem>)

    @Query("SELECT * FROM words WHERE id = :id LIMIT 1")
    suspend fun getWordById(id: Int): WordItem?

    @Query("SELECT * FROM words WHERE isLearned = 0")
    fun getUnlearnedWords(): Flow<List<WordItem>>

    @Query("SELECT * FROM words WHERE isLearned = 1")
    fun getLearnedWords(): Flow<List<WordItem>>

    @Update
    suspend fun updateWord(word: WordItem) // Kelimenin isLearned durumunu güncelleriz
}

