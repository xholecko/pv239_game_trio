package com.example.pv239_game_trio.frontend.games.tikBum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB
import com.example.pv239_game_trio.backend.entities.PlayerEntity

class TikBumActivity : AppCompatActivity() {

    private val TAG = "GameTrioTikBum"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tik_bum)
        Log.d(TAG,"TikBumActivity created")

        var db = Room.databaseBuilder(applicationContext, AppDB :: class.java, "GameTrioDB").build()

        Thread {
            var player1 = PlayerEntity()
            player1.id = 1
            player1.name = "Stefan1"
            player1.points = 1

            var player2 = PlayerEntity()
            player2.id = 2
            player2.name = "Stefan2"
            player2.points = 2

            db.playerDAO().create(player1)
            db.playerDAO().create(player2)

            db.playerDAO().showAllPlayers().forEach {
                Log.d(TAG,"Player : " + it.id + " " + it.name + " " + it.points)

            }

            db.playerDAO().deleteAllPlayers()


        }.start()

    }
}
