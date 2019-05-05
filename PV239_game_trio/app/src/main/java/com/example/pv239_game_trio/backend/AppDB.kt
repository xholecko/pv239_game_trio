package com.example.pv239_game_trio.backend

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pv239_game_trio.backend.dao.PlayerDAO
import com.example.pv239_game_trio.backend.entities.PlayerEntity

@Database (entities = [(PlayerEntity::class)],version = 1)
abstract class AppDB : RoomDatabase() {

    abstract fun playerDAO() : PlayerDAO
}