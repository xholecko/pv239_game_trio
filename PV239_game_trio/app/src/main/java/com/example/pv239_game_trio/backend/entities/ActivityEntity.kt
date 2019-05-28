package com.example.pv239_game_trio.backend.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ActivityEntity (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var text : String = ""
)