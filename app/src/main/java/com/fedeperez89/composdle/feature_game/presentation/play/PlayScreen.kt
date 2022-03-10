package com.fedeperez89.composdle.feature_game.presentation.play

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fedeperez89.composdle.feature_game.presentation.play.components.BoardItem
import com.fedeperez89.composdle.feature_game.presentation.play.components.KeyboardItem

@Composable
fun PlayScreen(
    viewModel: PlayViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Scaffold(Modifier.padding(8.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(64.dp)) {
            BoardItem(state.words)
            KeyboardItem {
                val event = when (it) {
                    "ENTER" -> PlayEvent.EnterPressed
                    "<=" -> PlayEvent.BackspacePressed
                    else -> PlayEvent.LetterPressed(it.first())
                }
                viewModel.onEvent(event)
            }
        }

    }
}