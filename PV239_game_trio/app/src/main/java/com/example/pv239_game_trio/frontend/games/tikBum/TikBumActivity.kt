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



        /// SOME DB TESTING, WILL DELETE LATER WHEN PROPER TESTS ARE DONE
        Thread {
            Log.d(TAG,"1")

            var db = Room.databaseBuilder<AppDB>(applicationContext, AppDB :: class.java, "GameTrioDB").build()

            Log.d(TAG,"2")

            //db.playerDAO().deleteAllPlayers()
            Log.d(TAG,"3")

            var player1 = PlayerEntity()
            player1.id = 1
            player1.name = "Peter"
            player1.points = 1

            var player2 = PlayerEntity()
            player2.id = 2
            player2.name = "Martin"
            player2.points = 2
            Log.d(TAG,"4")

            db.playerDAO().create(player1)
            Log.d(TAG,"9")

            db.playerDAO().create(player2)
            Log.d(TAG,"8")


            db.playerDAO().showAllPlayers().forEach {
                Log.d(TAG,"Player 2* : " + it.id + " " + it.name + " " + it.points)

            }


//            db.playerDAO().resetPointAllPlayers()
//            db.playerDAO().showAllPlayers().forEach {
//                Log.d(TAG,"Zero points all* : " + it.id + " " + it.name + " " + it.points)
//
//            }


            db.playerDAO().deleteAllPlayers()

            db.playerDAO().create(player1)



            player1.name = "noveMeno"
            db.playerDAO().update(player1)

            db.playerDAO().showAllPlayers().forEach {
                Log.d(TAG,"NewName : " + it.id + " " + it.name + " " + it.points)

            }

            db.playerDAO().create(player2)

            var foundPlayerId = db.playerDAO().findPlayerById(player2.id)
            Log.d(TAG,"Founded player ID : " + foundPlayerId.id + " " + foundPlayerId.name + " " + foundPlayerId.points)

            var foundPlayerName = db.playerDAO().findPlayerByName(player2.name)
            Log.d(TAG,"Founded player Name : " + foundPlayerName.id + " " + foundPlayerName.name + " " + foundPlayerName.points)

            var points = db.playerDAO().getPointsById(player2.id)
            Log.d(TAG,"points : " + points)

            db.playerDAO().addPointsById(player2.id,-155)
            points = db.playerDAO().getPointsById(player2.id)
            Log.d(TAG,"points : " + points)

            db.playerDAO().delete(player2)

            db.playerDAO().showAllPlayers().forEach {
                Log.d(TAG,"Player 1* : " + it.id + " " + it.name + " " + it.points)
           }




        }.start()

    }
}
