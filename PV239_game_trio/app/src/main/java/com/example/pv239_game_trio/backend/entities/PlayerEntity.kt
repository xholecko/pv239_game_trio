package com.example.pv239_game_trio.backend.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PlayerEntity {

    @PrimaryKey
    var id: Int = 0

    var name : String = ""
    var points : Int = 0

}