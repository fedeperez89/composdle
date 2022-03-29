package com.fedeperez89.composdle.feature_game.domain.use_case

import com.fedeperez89.composdle.feature_game.domain.repository.WordRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate

class WordOfTheDayUseCaseTest {

    @Test
    fun `Given a word of the day, then use case should convert to CharArray`() = runBlocking {
        val wordRepository = mockk<WordRepository> {
            coEvery { wordForDay(any()) } returns "ROATE"
        }

        val useCase = WordOfTheDayUseCase(wordRepository)
        assertEquals(listOf('R', 'O', 'A', 'T', 'E'), useCase())
    }


    @Test
    fun `Given an exact date, then use case should return always the same value`() = runBlocking {
        val today = LocalDate.now()
        val wordRepository = mockk<WordRepository> {
            coEvery { wordForDay(today) } returns "ROATE"
        }
        val useCase = WordOfTheDayUseCase(wordRepository)
        assertEquals(useCase(), useCase())
    }
}