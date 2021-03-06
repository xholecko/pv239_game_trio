package com.example.pv239_game_trio.backend

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pv239_game_trio.backend.dao.CharadeDAO
import com.example.pv239_game_trio.backend.dao.HangmanDAO
import com.example.pv239_game_trio.backend.dao.PlayerDAO
import com.example.pv239_game_trio.backend.dao.TikBumDAO
import com.example.pv239_game_trio.backend.entities.PlayerEntity
import com.example.pv239_game_trio.backend.entities.TikBumEntity
import com.example.pv239_game_trio.backend.entities.CharadeEntity
import com.example.pv239_game_trio.backend.entities.HangmanEntity


@Database (entities = [PlayerEntity::class,TikBumEntity::class,HangmanEntity::class,CharadeEntity::class],version = 2)
abstract class AppDB : RoomDatabase() {

    abstract fun playerDAO() : PlayerDAO
    abstract fun tikBumDAO() : TikBumDAO
    abstract fun charadeDAO() : CharadeDAO
    abstract fun hangmanDAO() : HangmanDAO

    companion object {
        private var INSTANCE: AppDB? = null
        fun getInstance(context: Context): AppDB {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    "app-database.db"
                )
                    .build()
            }
            return INSTANCE as AppDB
        }
    }
}