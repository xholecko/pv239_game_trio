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
import com.example.pv239_game_trio.frontend.main.ChooseGameActivity

class MainActivity : AppCompatActivity() {


    private val TAG = "GameTrioMainActivity"

    private lateinit var  startActivityButton: Button
    private lateinit var  addPlayerButton: Button
    private lateinit var  removePlayerButton: Button
    private lateinit var  restartGameButton: Button
    private lateinit var  resetPointsButton: Button

    private lateinit var  infoText: TextView
    private lateinit var  playersTextView: TextView
    private lateinit var db : AppDB
    private var playersInDb : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"onCreate()")

        db = Room.databaseBuilder<AppDB>(applicationContext, AppDB :: class.java, "GameTrioDB").build()

        addPlayerButton = findViewById(R.id.button_add)

        startActivityButton = findViewById(R.id.button_start)
        removePlayerButton = findViewById(R.id.button_remove_player)
        restartGameButton = findViewById(R.id.button_restart_game)
        resetPointsButton = findViewById(R.id.button_reset_points)

        infoText = findViewById(R.id.textView_info)
        playersTextView = findViewById(R.id.textView_players)

        Thread {
            playersTextView.text = getAllPlayers()
            if (db.playerDAO().showAllPlayers().size >= 6){
                addPlayerButton.visibility = View.INVISIBLE
            }
            if (db.playerDAO().showAllPlayers().size == 0){
                removePlayerButton.visibility = View.INVISIBLE
            }
            if(db.playerDAO().showAllPlayers().size < 2){
                removePlayerButton.visibility = View.INVISIBLE

            }
        }.start()




        addPlayerButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button ADD PLAYER was pressed")
            openActivityAddPlayerActivity()
        })

        startActivityButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button START was pressed")
            openActivityStartActivity()
        })

        removePlayerButton.setOnClickListener(View.OnClickListener {
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
                    openActivityMain()
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
    private fun openActivityMain(){
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }




    private fun getAllPlayers(): String {
        var output = "Players: "

        playersInDb = db.playerDAO().showAllPlayers().size
        Log.d(TAG, "Players in DB = " + playersInDb)


        for(i in 0 until playersInDb){
            output = output + "NAME = " + db.playerDAO().showAllPlayers()[i].name +
                    " POINTS = " + db.playerDAO().showAllPlayers()[i].points + ","
            Log.d(TAG, "Name= " + db.playerDAO().showAllPlayers()[i].name + " Id= " + db.playerDAO().showAllPlayers()[i].id)

        }

        return output
    }
}

