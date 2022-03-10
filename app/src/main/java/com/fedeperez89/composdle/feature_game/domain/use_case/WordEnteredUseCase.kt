package com.fedeperez89.composdle.feature_game.domain.use_case

import com.fedeperez89.composdle.feature_game.domain.repository.WordRepository
import javax.inject.Inject

class WordEnteredUseCase @Inject constructor(private val wordsRepository: WordRepository) {

    suspend operator fun invoke(word: String) {
        if (wordsRepository.wordExists(word)){

        }
    }
}