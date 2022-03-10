package com.fedeperez89.composdle.feature_game.data.repository

import com.fedeperez89.composdle.feature_game.domain.repository.WordRepository
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Inject

class InMemoryWordRepository @Inject constructor() : WordRepository {

    private val words = setOf("ROATE", "SHOCK", "MONTH", "QUERY")

    override suspend fun wordExists(word: String): Boolean = words.contains(word)

    override suspend fun randomWord(): String = words.stream()
        .skip(
            ThreadLocalRandom.current()
                .nextInt(words.size).toLong()
        )
        .findAny().orElse("MONTH")
}