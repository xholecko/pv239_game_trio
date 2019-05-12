package com.example.pv239_game_trio.backend.dto

class PlayerDTO{
    var id: Int = 0

    var name : String = ""

    var points : Int = 0

    var team : Int = 0
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayerDTO

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        return result
    }

    override fun toString(): String {
        return "Name='$name', points=$points, team=$team)"
    }


}