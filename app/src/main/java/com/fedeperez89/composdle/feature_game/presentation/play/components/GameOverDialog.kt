package com.fedeperez89.composdle.feature_game.presentation.play.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.fedeperez89.composdle.R

@Composable
fun GameOverDialog(gameWon: Boolean, wordOfTheDay: String) {
    val title = if (gameWon) stringResource(R.string.well_done) else stringResource(R.string.better_luck)
    val content =
        if (gameWon) stringResource(R.string.well_done_message) else stringResource(R.string.word_was, wordOfTheDay)
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = title) },
        text = {
            Text(
                text = content
            )
        }, confirmButton = {}
    )
}