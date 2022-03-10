package com.fedeperez89.composdle.feature_game.presentation.play

import com.fedeperez89.composdle.feature_game.presentation.play.components.LetterItemState
import com.fedeperez89.composdle.feature_game.presentation.play.components.WordItemState

data class PlayState(
    val words: List<WordItemState> = listOf(
        WordItemState(
            letters = listOf(
                LetterItemState(label = null),
                LetterItemState(label = null),
                LetterItemState(label = null),
                LetterItemState(label = null),
                LetterItemState(label = null),
            )
        ),
        WordItemState(
            letters = listOf(
                LetterItemState(label = null),
                LetterItemState(label = null),
                LetterItemState(label = null),
                LetterItemState(label = null),
                LetterItemState(label = null),
            )
        ), WordItemState(
            letters = listOf(
                LetterItemState(label = null),
                LetterItemState(label = null),
                LetterItemState(label = null),
                LetterItemState(label = null),
                LetterItemState(label = null),
            )
        ), WordItemState(
            letters = listOf(
                LetterItemState(label = null),
                LetterItemState(label = null),
                LetterItemState(label = null),
                LetterItemState(label = null),
                LetterItemState(label = null),
            )
        ), WordItemState(
            letters = listOf(
                LetterItemState(label = null),
                LetterItemState(label = null),
                LetterItemState(label = null),
                LetterItemState(label = null),
                LetterItemState(label = null),
            )
        )
    ),
    val usedLetters: Set<Char> = emptySet()
)