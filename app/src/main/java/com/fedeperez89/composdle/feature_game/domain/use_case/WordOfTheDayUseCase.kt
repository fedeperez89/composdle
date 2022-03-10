package com.fedeperez89.composdle.feature_game.domain.use_case

import com.fedeperez89.composdle.feature_game.domain.repository.WordRepository
import javax.inject.Inject

class WordOfTheDayUseCase @Inject constructor(private val wordRepository: WordRepository) {

    suspend operator fun invoke(): String {
        return wordRepository.randomWord()
    }
}