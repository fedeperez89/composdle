package com.fedeperez89.composdle.feature_game.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fedeperez89.composdle.feature_game.domain.model.Word

@Dao
interface WordsDao {

    @Query("SELECT value FROM word")
    fun getAll(): List<String>

    @Query("SELECT value FROM word limit 1")
    suspend fun getOne(): String?

    @Query("SELECT value FROM word where value = :word")
    suspend fun find(word: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<Word>)
}