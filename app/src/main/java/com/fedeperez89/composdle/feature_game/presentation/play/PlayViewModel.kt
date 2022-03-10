package com.fedeperez89.composdle.feature_game.presentation.play

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedeperez89.composdle.feature_game.domain.use_case.WordOfTheDayUseCase
import com.fedeperez89.composdle.feature_game.presentation.play.components.LetterItemState
import com.fedeperez89.composdle.feature_game.presentation.play.components.LetterState
import com.fedeperez89.composdle.feature_game.presentation.play.components.WordItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(private val wordOfTheDayUseCase: WordOfTheDayUseCase) : ViewModel() {

    private val _state = mutableStateOf(PlayState())
    val state: State<PlayState> = _state

    private var wordOfTheDay = CharArray(5)
    private var currentWordIndex = 0
    private var currentWord: Array<Char?> = arrayOfNulls(5)

    private var currentLetterIndex = 0

    init {
        viewModelScope.launch {
            wordOfTheDay = wordOfTheDayUseCase().toCharArray()
        }
    }

    fun onEvent(event: PlayEvent) {
        when (event) {
            is PlayEvent.LetterPressed -> {
                if (currentLetterIndex <= 4) {
                    currentWord[currentLetterIndex] = event.letter

                    updateWordState()

                    if (currentLetterIndex != 4) {
                        currentLetterIndex++
                    }
                }
            }
            PlayEvent.BackspacePressed -> {
                if (currentLetterIndex > 0) {
                    if (currentWord[currentLetterIndex] == null) {
                        currentLetterIndex--
                    } else {
                        currentWord[currentLetterIndex] = null
                    }
                    updateWordState()
                }
            }
            PlayEvent.EnterPressed -> {
                if (currentLetterIndex == 4) {
                    updateWordState(submit = true)
                    currentWordIndex++
                    currentLetterIndex = 0
                    currentWord = arrayOfNulls(5)
                }
            }
        }
    }

    private fun updateWordState(submit: Boolean = false) {
        val words = _state.value.words
        val updatedWords = words.toMutableList()
        updatedWords[currentWordIndex] = WordItemState(
            letters = currentWord.mapIndexed { index, letter ->
                val state = when {
                    !submit -> LetterState.NOT_SUBMITTED
                    letter == wordOfTheDay[index] -> LetterState.OK
                    wordOfTheDay.contains(
                        letter ?: '0'
                    ) -> LetterState.POSITION
                    else -> LetterState.INCORRECT
                }
                LetterItemState(label = letter, state = state)
            }
        )

        _state.value = _state.value.copy(words = updatedWords)
    }

    private fun Set<Char>.addLetter(letter: Char): Set<Char> {
        return toMutableSet().apply {
            add(letter)
        }
    }
}