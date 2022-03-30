package com.fedeperez89.composdle.feature_game.data.repository

import com.fedeperez89.composdle.feature_game.data.data_source.WordClient
import com.fedeperez89.composdle.feature_game.data.data_source.WordsDao
import com.fedeperez89.composdle.feature_game.domain.model.Word
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class WordRepositoryImplTest {

    @MockK
    lateinit var wordsDao: WordsDao

    @MockK
    lateinit var wordClient: WordClient

    @InjectMockKs
    lateinit var repository: WordRepositoryImpl

    @Before
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Given a word that exists, repository should return true`() = runTest {
        coEvery { wordsDao.find("exist") } returns "exist"

        repository.wordExists("exist")

        coVerify { wordsDao.find("exist") }
    }

    @Test
    fun `Given a word that does not exist, repository should return false`() = runTest {
        coEvery { wordsDao.find("zzzzz") } returns "zzzzz"

        repository.wordExists("zzzzz")

        coVerify { wordsDao.find("zzzzz") }
    }


    @Test
    fun `Given a populated database, repository should not use client`() = runTest {
        coEvery { wordsDao.getOne() } returns "zzzzz"
        coEvery { wordsDao.getAll() } returns listOf("AAAAA", "BBBBB")

        repository.wordForDay(LocalDate.now())

        verify { wordClient wasNot Called }
    }

    @Test
    fun `Given an empty database, repository should populate db`() = runTest {
        coEvery { wordsDao.getOne() } returns null
        coEvery { wordClient.getWordList() } returns "AAAAA\nBBBBB"
        coJustRun { wordsDao.insertAll(listOf("AAAAA", "BBBBB").map { Word(it) }) }

        repository.wordForDay(LocalDate.now())

        coVerify { wordClient.getWordList() }
    }

    @Test
    fun `Given words that are not 5 char long, they should be filtered out`() = runTest {
        coEvery { wordsDao.getOne() } returns null
        coEvery { wordClient.getWordList() } returns "THISDOESNTBELONG\nBBBBB"
        coJustRun { wordsDao.insertAll(listOf("BBBBB").map { Word(it) }) }

        val wordOfTheDay = repository.wordForDay(LocalDate.now())

        assertEquals("BBBBB", wordOfTheDay)
        coVerify { wordClient.getWordList() }
    }
}