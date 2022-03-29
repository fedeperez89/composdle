package com.fedeperez89.composdle.feature_game.presentation.play.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LetterItem(
    modifier: Modifier = Modifier,
    state: LetterItemState
) {
    val boxModifier = if (state.state == LetterState.NOT_SUBMITTED) {
        modifier.border(2.dp, Color.LightGray)
    } else {
        modifier
            .background(Color(android.graphics.Color.parseColor(state.state.backgroundColor)))
    }
    Box(
        modifier = boxModifier,
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
                fontWeight = FontWeight.Bold,
                text = state.label?.toString() ?: "",
                fontSize = 30.sp,
                color = state.state.letterColor
            )
        }
    }
}

data class LetterItemState(val label: Char?, val state: LetterState = LetterState.NOT_SUBMITTED)

enum class LetterState(val backgroundColor: String, val letterColor: Color) {
    OK("#6AAA64", Color.White),
    POSITION("#C9B458", Color.White),
    NOT_IN_WORD("#787C7E", Color.White),
    NOT_SUBMITTED("#FFFFFFFF", Color.Black)
}
