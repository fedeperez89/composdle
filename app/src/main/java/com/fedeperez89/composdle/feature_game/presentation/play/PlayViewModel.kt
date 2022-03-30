package com.fedeperez89.composdle.feature_game.presentation.play

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedeperez89.composdle.R
import com.fedeperez89.composdle.feature_game.domain.use_case.*
import com.fedeperez89.composdle.feature_game.presentation.play.components.KeyState
import com.fedeperez89.composdle.feature_game.presentation.play.components.LetterItemState
import com.fedeperez89.composdle.feature_game.presentation.play.components.LetterState
import com.fedeperez89.composdle.feature_game.presentation.play.components.WordItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val wordOfTheDayUseCase: WordOfTheDayUseCase,
    private val wordEnteredUseCase: WordEnteredUseCase
) : ViewModel() {

    private val _state = mutableStateOf(PlayState())
    val state: State<PlayState> = _state

    private var wordOfTheDay: List<Char> = emptyList()
    private var currentWordIndex = 0
    private var currentWord: MutableList<Char?> = MutableList(5) { null }

    private var currentLetterIndex = 0

    init {
        viewModelScope.launch {
            wordOfTheDay = wordOfTheDayUseCase()
        }
    }

    fun onEvent(event: PlayEvent) = viewModelScope.launch {
        if (!isGameOver())
            when (event) {
                is PlayEvent.LetterPressed -> onLetterPressed(event.letter)
                PlayEvent.BackspacePressed -> onBackspacePressed()
                PlayEvent.EnterPressed -> onWordSubmit()
            }
    }

    private suspend fun onLetterPressed(letter: Char) {
        if (currentLetterIndex <= 4 && currentWord.last() == null) {
            currentWord[currentLetterIndex] = letter

            when (val currentResult = wordEnteredUseCase(currentWord, wordOfTheDay)) {
                WordEnteredUseCase.WordEnteredResult.NotAWord -> {}
                is WordEnteredUseCase.WordEnteredResult.WordOK -> updateWordState(currentResult.data, submit = false)
            }
            if (currentLetterIndex != 4) {
                currentLetterIndex++
            }
        }
    }

    private suspend fun onBackspacePressed() {
        if (currentLetterIndex > 0) {
            if (currentWord[currentLetterIndex] == null) {
                currentLetterIndex--
            }
            currentWord[currentLetterIndex] = null
            when (val currentResult = wordEnteredUseCase(currentWord, wordOfTheDay)) {
                WordEnteredUseCase.WordEnteredResult.NotAWord -> {}
                is WordEnteredUseCase.WordEnteredResult.WordOK -> updateWordState(currentResult.data, submit = false)
            }
        }
    }

    private suspend fun onWordSubmit() {
        if (currentLetterIndex == 4) {
            when (val currentResult = wordEnteredUseCase(currentWord, wordOfTheDay, verify = true)) {
                is WordEnteredUseCase.WordEnteredResult.WordOK -> {
                    updateWordState(currentResult.data, submit = true)
                    currentWordIndex++
                    currentLetterIndex = 0
                    currentWord = MutableList(5) { null }
                    _state.value =
                        _state.value.copy(isGameOver = currentWordIndex > 5 || currentResult.data.all { it is Ok })
                }
                is WordEnteredUseCase.WordEnteredResult.NotAWord -> {
                    _state.value = _state.value.copy(message = R.string.not_a_word)
                }
            }
        }
    }

    private fun updateWordState(currentResult: List<LetterPosition>, submit: Boolean = false) {
        val words = _state.value.words
        val updatedWords = words.toMutableList()
        updatedWords[currentWordIndex] = WordItemState(
            letters = currentResult.map {
                val state = when (it) {
                    is Ok -> LetterState.OK
                    is Incorrect -> LetterState.POSITION
                    is NotInWord -> LetterState.NOT_IN_WORD
                }
                LetterItemState(label = it.letter, if (submit) state else LetterState.NOT_SUBMITTED)
            }
        )
        val usedLetters = if (submit) {
            val addedLetters = _state.value.lettersState.toMutableMap()
            currentResult.forEach {
                val value = when (it) {
                    is Incorrect -> KeyState.POSITION
                    is NotInWord -> KeyState.NOT_IN_WORD
                    is Ok -> KeyState.OK
                }
                addedLetters[it.letter.toString()] = value
            }
            addedLetters
        } else {
            _state.value.lettersState
        }

        _state.value = _state.value.copy(words = updatedWords, lettersState = usedLetters)
    }

    private fun isGameOver() = _state.value.isGameOver
}