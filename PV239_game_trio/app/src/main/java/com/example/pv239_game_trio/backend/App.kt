package com.example.pv239_game_trio.backend

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.pv239_game_trio.backend.entities.PlayerEntity
import com.example.pv239_game_trio.backend.entities.TikBumEntity

class App : Application() {
    private val TAG = "GameTrioApp"



    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"onCreate()")
        val db = Room.databaseBuilder<AppDB>(applicationContext, AppDB :: class.java, "GameTrioDB").build()

        //createPlayers(db)
        createWords(db)
    }




    private fun createPlayers(db: AppDB){
        Thread {
            var player1 = PlayerEntity()
            player1.id = 1
            player1.name = "ADMIN"
            player1.points = 0

            db.playerDAO().create(player1)

            Log.d(TAG,"1 player created")

        }.start()
    }

    private fun createWords(db: AppDB){
        Thread {
            var word0 = TikBumEntity()
            word0.id = 7
            word0.word = "lol"

            var word1 = TikBumEntity()
            word1.id = 1
            word1.word = "ha"

            var word2 = TikBumEntity()
            word2.id = 2
            word2.word = "na"

            var word3 = TikBumEntity()
            word3.id = 3
            word3.word = "ba"

            var word4 = TikBumEntity()
            word4.id = 4
            word4.word = "fa"

            var word5 = TikBumEntity()
            word5.id = 5
            word5.word = "ra"

            var word6 = TikBumEntity()
            word6.id = 6
            word6.word = "la"

            db.tikBumDAO().create(word0)
            db.tikBumDAO().create(word1)
            db.tikBumDAO().create(word2)
            db.tikBumDAO().create(word3)
            db.tikBumDAO().create(word4)
            db.tikBumDAO().create(word5)
            db.tikBumDAO().create(word6)
            Log.d(TAG,"7 words created")

        }.start()
    }
}
