package com.fedeperez89.composdle.feature_game.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
    @PrimaryKey val value: String,
)