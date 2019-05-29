package com.example.pv239_game_trio.backend.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HangmanEntity (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var word : String = ""
)