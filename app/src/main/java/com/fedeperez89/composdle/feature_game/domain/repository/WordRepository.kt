package com.fedeperez89.composdle.feature_game.domain.repository

import java.time.LocalDate

interface WordRepository {

    suspend fun wordExists(word: String): Boolean

    suspend fun wordForDay(date: LocalDate): String

}