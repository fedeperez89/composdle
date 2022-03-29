package com.fedeperez89.composdle.feature_game.presentation.play.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KeyItem(modifier: Modifier = Modifier, letter: String, state: KeyState, onClick: (String) -> Unit) {
    Card(
        modifier = modifier,
        backgroundColor = state.backgroundColor,
        onClick = { onClick(letter) }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                text = letter,
                fontSize = 12.sp,
                color = state.letterColor
            )
        }
    }
}

@Composable
fun KeyRow(keys: List<String>, lettersState: Map<String, KeyState>, onKeyClicked: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        keys.forEach { it ->
            KeyItem(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(0.75f), letter = it,
                state = lettersState[it] ?: KeyState.UNUSED,
                onClick = onKeyClicked
            )
        }
    }
}

@Composable
fun KeyboardItem(lettersState: Map<String, KeyState>, onKeyClicked: (String) -> Unit) {
    val topRow = listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P")
    val middleRow = listOf("A", "S", "D", "F", "G", "H", "J", "K", "L")
    val bottomRow = listOf("<=", "Z", "X", "C", "V", "B", "N", "M", "K", "L", "ENTER")
    val keyboard = listOf(topRow, middleRow, bottomRow)

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        keyboard.forEach {
            KeyRow(keys = it, lettersState = lettersState, onKeyClicked = onKeyClicked)
        }
    }
}

enum class KeyState(val backgroundColor: Color, val letterColor: Color) {
    OK(Color(android.graphics.Color.parseColor("#6AAA64")), Color.White),
    POSITION(Color(android.graphics.Color.parseColor("#C9B458")), Color.White),
    NOT_IN_WORD(Color(android.graphics.Color.parseColor("#787C7E")), Color.White),
    UNUSED(Color.LightGray, Color.White)
}
