package com.smtersoyoglu.shuffleandlearncompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smtersoyoglu.shuffleandlearncompose.data.local.entity.WordEntity

@Database(entities = [WordEntity::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}
