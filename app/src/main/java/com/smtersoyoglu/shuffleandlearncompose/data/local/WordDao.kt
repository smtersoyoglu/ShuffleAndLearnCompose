package com.smtersoyoglu.shuffleandlearncompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smtersoyoglu.shuffleandlearncompose.data.local.entity.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<WordEntity>)

    @Query("SELECT * FROM words")
    fun getAllWords(): Flow<List<WordEntity>>

    @Query("SELECT * FROM words WHERE id = :id LIMIT 1")
    suspend fun getWordById(id: Int): WordEntity?

    @Query("SELECT * FROM words WHERE isLearned = 0")
    fun getUnlearnedWords(): Flow<List<WordEntity>>

    @Query("SELECT * FROM words WHERE isLearned = 1")
    fun getLearnedWords(): Flow<List<WordEntity>>

    @Query("UPDATE words SET isLearned = :isLearned WHERE id = :id")
    suspend fun updateLearnedStatus(id: Int, isLearned: Boolean) // Kelimenin isLearned durumunu güncelleriz
}

