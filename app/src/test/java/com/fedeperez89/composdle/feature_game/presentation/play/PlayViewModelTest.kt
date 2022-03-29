package com.fedeperez89.composdle.feature_game.presentation.play

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.fedeperez89.composdle.feature_game.domain.repository.WordRepository
import com.fedeperez89.composdle.feature_game.domain.use_case.WordEnteredUseCase
import com.fedeperez89.composdle.feature_game.domain.use_case.WordOfTheDayUseCase
import com.fedeperez89.composdle.feature_game.presentation.play.components.LetterState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PlayViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @Test
    fun `Given a newly created viewModel, state should be empty`() = runTest {
        val wordRepository = mockk<WordRepository> {
            coEvery { wordForDay(any()) } returns "MONTH"
        }
        val playViewModel = PlayViewModel(WordOfTheDayUseCase(wordRepository), WordEnteredUseCase(wordRepository))
        val value = playViewModel.state.value

        assertTrue(value.lettersState.isEmpty())
        assertEquals(LetterState.NOT_SUBMITTED, value.words.first().letters.first().state)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }
}