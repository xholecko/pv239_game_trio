package com.example.pv239_game_trio.backend.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TikBumEntity {

    @PrimaryKey
    var id: Int = 0
    var word : String = ""


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TikBumEntity

        if (id != other.id) return false
        if (word != other.word) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + word.hashCode()
        return result
    }


}