package com.fedeperez89.composdle.feature_game.presentation.play.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WordItem(state: WordItemState) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        state.letters.forEachIndexed { index, item ->
            LetterItem(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f), state = item,
                delayStart = index * 200
            )
        }
    }
}

data class WordItemState(
    val letters: List<LetterItemState>
)