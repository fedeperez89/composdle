package com.fedeperez89.composdle.feature_game.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fedeperez89.composdle.feature_game.domain.model.Word

@Database(entities = [Word::class], version = 1)
abstract class WordDatabase : RoomDatabase() {
    abstract val shotsDao: WordsDao

    companion object {
        const val DATABASE_NAME = "words.db"
    }
}