package com.smtersoyoglu.shuffleandlearncompose.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smtersoyoglu.shuffleandlearncompose.data.model.WordItem

@Database(entities = [WordItem::class], version = 1)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}