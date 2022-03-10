package com.fedeperez89.composdle.feature_game.presentation.play

sealed class PlayEvent {
    data class LetterPressed(val letter: Char) : PlayEvent()
    object EnterPressed : PlayEvent()
    object BackspacePressed : PlayEvent()
}