package com.fedeperez89.composdle.feature_game.presentation.play

import androidx.annotation.StringRes
import com.fedeperez89.composdle.feature_game.presentation.play.components.KeyState
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
    val lettersState: Map<String, KeyState> = emptyMap(),
    @StringRes val message: Int = 0,
)