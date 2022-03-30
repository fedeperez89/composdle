package com.fedeperez89.composdle.feature_game.data.repository

import com.fedeperez89.composdle.feature_game.data.data_source.WordClient
import com.fedeperez89.composdle.feature_game.data.data_source.WordsDao
import com.fedeperez89.composdle.feature_game.domain.model.Word
import com.fedeperez89.composdle.feature_game.domain.repository.WordRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val wordsDao: WordsDao,
    private val wordClient: WordClient
) : WordRepository {

    override suspend fun wordExists(word: String): Boolean = withContext(ioDispatcher) {
        wordsDao.find(word) != null
    }

    override suspend fun wordForDay(date: LocalDate): String = withContext(ioDispatcher) {
        val words = if (wordsDao.getOne() == null) {
            val wordList = wordClient.getWordList().split('\n')
                .map { it.uppercase() }
                .filter {
                    it.length == 5 && it.matches("[A-Z]*".toRegex())
                }
            launch {
                wordsDao.insertAll(wordList.map { Word(it) })
            }
            wordList
        } else {
            wordsDao.getAll()
        }
        words[date.toEpochDay().toInt() % words.size]
    }
}