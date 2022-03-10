package com.fedeperez89.composdle.feature_game.presentation.play.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LetterItem(
    modifier: Modifier = Modifier,
    state: LetterItemState
) {
    Box(
        modifier = modifier
            .background(state.state.color)
            .border(1.dp, Color.Gray),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                text = state.label?.toString() ?: "",
                fontSize = 30.sp,
                color = Color.Black
            )
        }
    }
}

data class LetterItemState(val label: Char?, val state: LetterState = LetterState.NOT_SUBMITTED)

enum class LetterState(val color: Color) {
    OK(Color.Green),
    POSITION(Color.Yellow),
    INCORRECT(Color.Gray),
    NOT_SUBMITTED(Color.Transparent)
}
