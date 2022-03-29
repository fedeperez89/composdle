package com.fedeperez89.composdle.feature_game.domain.use_case

import com.fedeperez89.composdle.feature_game.domain.repository.WordRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class WordEnteredUseCaseTest {

    @MockK
    lateinit var wordRepository: WordRepository

    @InjectMockKs
    lateinit var wordEnteredUseCase: WordEnteredUseCase

    @Before
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Given a correct word, result should be all OK`() = runTest {
        val wordEntered = "HELLO".toList()
        val wordOfTheDay = "HELLO".toList()
        when (val useCase = wordEnteredUseCase(wordEntered, wordOfTheDay)) {
            WordEnteredUseCase.WordEnteredResult.NotAWord -> fail("Word should be valid")
            is WordEnteredUseCase.WordEnteredResult.WordOK -> useCase.data.forEachIndexed { index, position ->
                assertEquals(Ok(wordEntered[index]), position)
            }
        }
    }

    @Test
    fun `Given a fully incorrect word, result should be all NOT_IN_WORD`() = runTest {
        val wordEntered = "ABCDF".toList()
        val wordOfTheDay = "HELLO".toList()
        when (val useCase = wordEnteredUseCase(wordEntered, wordOfTheDay)) {
            WordEnteredUseCase.WordEnteredResult.NotAWord -> fail("Word should be valid")
            is WordEnteredUseCase.WordEnteredResult.WordOK -> useCase.data.forEachIndexed { index, position ->
                assertEquals(NotInWord(wordEntered[index]), position)
            }
        }
    }

    @Test
    fun `Given letters with wrong sorting, result should be all Incorrect`() = runTest {
        val wordEntered = "LOHEL".toList()
        val wordOfTheDay = "HELLO".toList()
        when (val useCase = wordEnteredUseCase(wordEntered, wordOfTheDay)) {
            WordEnteredUseCase.WordEnteredResult.NotAWord -> fail("Word should be valid")
            is WordEnteredUseCase.WordEnteredResult.WordOK -> useCase.data.forEachIndexed { index, position ->
                assertEquals(Incorrect(wordEntered[index]), position)
            }
        }
    }

    @Test
    fun `Given letters with correct and incorrect letters, result should be mixed`() = runTest {
        val wordEntered = "AXCED".toList()
        val wordOfTheDay = "ABCDE".toList()
        when (val useCase = wordEnteredUseCase(wordEntered, wordOfTheDay)) {
            WordEnteredUseCase.WordEnteredResult.NotAWord -> fail("Word should be valid")
            is WordEnteredUseCase.WordEnteredResult.WordOK -> {
                assertEquals(
                    listOf(
                        Ok('A'),
                        NotInWord('X'),
                        Ok('C'),
                        Incorrect('E'),
                        Incorrect('D')
                    ),
                    useCase.data
                )
            }
        }
    }

    @Test
    fun `Given repeated letters, result should consider usages`() = runTest {
        val wordEntered = "MONOO".toList()
        val wordOfTheDay = "MOONS".toList()
        when (val useCase = wordEnteredUseCase(wordEntered, wordOfTheDay)) {
            WordEnteredUseCase.WordEnteredResult.NotAWord -> fail("Word should be valid")
            is WordEnteredUseCase.WordEnteredResult.WordOK -> {
                assertEquals(
                    listOf(
                        Ok('M'),
                        Ok('O'),
                        Incorrect('N'),
                        Incorrect('O'),
                        NotInWord('O')
                    ),
                    useCase.data
                )
            }
        }
    }
}