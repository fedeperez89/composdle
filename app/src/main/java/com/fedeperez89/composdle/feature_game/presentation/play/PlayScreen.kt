package com.fedeperez89.composdle.feature_game.presentation.play

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fedeperez89.composdle.feature_game.presentation.play.components.BoardItem
import com.fedeperez89.composdle.feature_game.presentation.play.components.GameOverDialog
import com.fedeperez89.composdle.feature_game.presentation.play.components.KeyboardItem

@Composable
fun PlayScreen(
    viewModel: PlayViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state.value
    Scaffold(
        modifier = Modifier.padding(8.dp),
        scaffoldState = scaffoldState
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(64.dp)) {
            if (state.message != 0) {
                val message = stringResource(id = state.message)
                LaunchedEffect(state.message) {
                    scaffoldState.snackbarHostState.showSnackbar(message)
                }
            }
            BoardItem(state.words)
            KeyboardItem(lettersState = state.lettersState) {
                val event = when (it) {
                    "ENTER" -> PlayEvent.EnterPressed
                    "<=" -> PlayEvent.BackspacePressed
                    else -> PlayEvent.LetterPressed(it.first())
                }
                viewModel.onEvent(event)
            }
            if (state.gameState != GameState.Playing) {
                GameOverDialog(state.gameState == GameState.Won, state.wordOfTheDay)
            }
        }
    }
}