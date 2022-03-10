package com.fedeperez89.composdle.feature_game.domain.repository

interface WordRepository {

    suspend fun wordExists(word: String): Boolean

    suspend fun randomWord(): String

}