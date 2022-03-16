package com.fedeperez89.composdle.feature_game.domain.use_case

import com.fedeperez89.composdle.feature_game.domain.repository.WordRepository
import javax.inject.Inject

class WordEnteredUseCase @Inject constructor(private val wordsRepository: WordRepository) {

    sealed class WordEnteredResult {
        data class WordOK(val data: Array<LetterPosition>) : WordEnteredResult()
        object NotAWord : WordEnteredResult()
    }

    suspend operator fun invoke(word: Array<Char?>, wordOfTheDay: CharArray, verify: Boolean = false): WordEnteredResult {
        if (verify && !wordsRepository.wordExists(word.joinToString(separator = ""))) {
            return WordEnteredResult.NotAWord
        }
        val result = Array<LetterPosition>(word.size) { Incorrect('0') }
        val letterCount = wordOfTheDay.groupBy { it }.toMutableMap()
        wordOfTheDay.mapIndexed { index, char ->
            val letterToCheck = word[index]
            result[index] = when {
                letterToCheck == char -> {
                    Ok(letterToCheck)
                }
                letterCount.containsKey(letterToCheck) -> {
                    Incorrect(letterToCheck)
                }
                else -> {
                    NotInWord(letterToCheck)
                }
            }
            letterToCheck?.let {
                letterCount.computeIfPresent(letterToCheck) { key, value ->
                    val res = value.toMutableList().apply {
                        removeFirstOrNull()
                    }
                    res.ifEmpty { null }
                }
            }
        }
        return WordEnteredResult.WordOK(result)
    }
}

sealed class LetterPosition(open val letter: Char?)
data class NotInWord(override val letter: Char?) : LetterPosition(letter)
data class Incorrect(override val letter: Char?) : LetterPosition(letter)
data class Ok(override val letter: Char?) : LetterPosition(letter)
