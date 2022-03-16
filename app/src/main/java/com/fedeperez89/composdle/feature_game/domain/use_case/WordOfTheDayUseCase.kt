package com.fedeperez89.composdle.feature_game.domain.use_case

import com.fedeperez89.composdle.feature_game.domain.repository.WordRepository
import java.time.LocalDate
import javax.inject.Inject

class WordOfTheDayUseCase @Inject constructor(private val wordRepository: WordRepository) {

    suspend operator fun invoke(date: LocalDate = LocalDate.now()): CharArray {
        return wordRepository.wordForDay(date).toCharArray()
    }
}