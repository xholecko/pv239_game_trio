package com.example.pv239_game_trio.frontend.main.start

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.room.Room
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB
import com.example.pv239_game_trio.backend.entities.PlayerEntity
import com.example.pv239_game_trio.frontend.main.ChooseGameActivity

class MainActivity : AppCompatActivity() {


    private val TAG = "GameTrioMainActivity"


    private lateinit var  startActivityButton: Button
    private lateinit var  addPlayerButton: Button
    private lateinit var  removeTeamButton: Button
    private lateinit var  restartGameButton: Button
    private lateinit var  resetPointsButton: Button

    private lateinit var  infoText: TextView
    private lateinit var  teams: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_fragment)
        Log.d(TAG,"onCreate()")

        val db = Room.databaseBuilder<AppDB>(applicationContext, AppDB :: class.java, "GameTrioDB").build()
        createPlayers(db)

        addPlayerButton = findViewById(R.id.button_add)


        startActivityButton = findViewById(R.id.button_start)
        removeTeamButton = findViewById(R.id.button_remove_player)
        restartGameButton = findViewById(R.id.button_restart_game)
        resetPointsButton = findViewById(R.id.button_reset_points)

        infoText = findViewById(R.id.textView_info)
        teams = findViewById(R.id.textView_players)

        addPlayerButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button ADD PLAYER was pressed")
            openActivityAddPlayerActivity()
        })

        startActivityButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button START was pressed")
            openActivityStartActivity()
        })

        removeTeamButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button REMOVE PLAYER was pressed")
            openActivityRemovePlayerActivity()
        })


        resetPointsButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button RESET POINTS was pressed")

            val builder : AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to reset all points?")
            builder.setPositiveButton("Yes"){dialog, which ->
                Thread{
                    db.playerDAO().resetPointAllPlayers()
                    Log.d(TAG,"resetPointAllTeams() DONE")
                }.start()
            }
            builder.setNegativeButton("No"){dialog, which ->
                Log.d(TAG,"resetPointAllTeams() canceled by user")

            }
            builder.show()



        })

        restartGameButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button RESTART GAME was pressed")

            val builder : AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to restart game?")
            builder.setPositiveButton("Yes"){dialog, which ->
                Thread{
                    db.playerDAO().deleteAllPlayers()
                    Log.d(TAG,"deleteAllPlayers() DONE")
                }.start()
            }
            builder.setNegativeButton("No"){dialog, which ->
                Log.d(TAG,"deleteAllPlayers() canceled by user")

            }
            builder.show()
        })


    }



    private fun openActivityAddPlayerActivity(){
        val intent = Intent(this, AddPlayerActivity::class.java)
        startActivity(intent)
    }

    private fun openActivityStartActivity(){
        val intent = Intent(this, ChooseGameActivity::class.java)
        startActivity(intent)
    }

    private fun openActivityRemovePlayerActivity(){
        val intent = Intent(this, RemovePlayerActivity::class.java)
        startActivity(intent)
    }




    private fun createPlayers(db: AppDB){
        Thread {
            var player1 = PlayerEntity()
            player1.id = 0
            player1.name = "Peter"
            player1.points = 0


            var player2 = PlayerEntity()
            player2.id = 1
            player2.name = "Marcel"
            player2.points = 0


            var player3 = PlayerEntity()
            player3.id = 2
            player3.name = "Jitka"
            player3.points = 0


            var player4 = PlayerEntity()
            player4.id = 3
            player4.name = "Sara"
            player4.points = 0



            db.playerDAO().create(player1)
            db.playerDAO().create(player2)
            db.playerDAO().create(player3)
            db.playerDAO().create(player4)

            Log.d(TAG,"4 players created")

        }.start()
    }

}

