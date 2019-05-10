package com.example.pv239_game_trio.backend

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pv239_game_trio.backend.dao.PlayerDAO
import com.example.pv239_game_trio.backend.dao.TikBumDAO
import com.example.pv239_game_trio.backend.entities.PlayerEntity
import com.example.pv239_game_trio.backend.entities.TikBumEntity

@Database (entities = [PlayerEntity::class],version = 1)
abstract class AppDB : RoomDatabase() {

    abstract fun playerDAO() : PlayerDAO
    //abstract fun tikBumDAO() : TikBumDAO

//    companion object {
//        var TEST_MODE = false
//        private val databaseName = "GameTrioDB"
//
//        private var db: AppDB? = null
//        private var dbInstance: PlayerDAO? = null
//
//        fun getInstance(context: Context): PlayerDAO {
//            if (dbInstance == null) {
//                if (TEST_MODE) {
//                    db = Room.inMemoryDatabaseBuilder(context, AppDB::class.java).allowMainThreadQueries().build()
//                    dbInstance = db?.playerDAO()
//                } else {
//                    db = Room.databaseBuilder(context, AppDB::class.java, databaseName).build()
//                    dbInstance = db?.playerDAO()
//                }
//            }
//            return dbInstance!!
//        }
//
//        private fun close() {
//            db?.close()
//        }
//    }

//    companion object {
//        var INSTANCE: AppDB? = null
//
//        fun getAppDataBase(context: Context): AppDB? {
//            if (INSTANCE == null){
//                synchronized(AppDB::class){
//                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDB::class.java, "GameTrioDB").build()
//                }
//            }
//            return INSTANCE
//        }
//
//        fun destroyDataBase(){
//            INSTANCE = null
//        }
//    }
}