package com.example.pv239_game_trio.frontend.main.start

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.room.Room
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB
import com.example.pv239_game_trio.backend.entities.PlayerEntity
import com.example.pv239_game_trio.frontend.main.PlayersActivity

class MainActivity : AppCompatActivity() {


    private val TAG = "GameTrioMainActivity"
    private lateinit var players: Array<PlayerEntity>
    private lateinit var db : AppDB

    private lateinit var playButton: CardView
    private lateinit var showScoreButton: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate()")
        db = Room.databaseBuilder<AppDB>(applicationContext, AppDB::class.java, "GameTrioDB").build()
        getPlayers().execute()

        playButton = findViewById(R.id.play_button)
        showScoreButton = findViewById(R.id.show_score_button)

        playButton.setOnClickListener {
            Log.d(TAG, "button START was pressed")
            openActivityStartActivity()
        }

        showScoreButton.setOnClickListener {
            if (players.size > 0){
                openScoreActivity()
            }
            else{
                Toast.makeText(this, "There is no player.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openScoreActivity() {
        val intent = Intent(this, ScoreActivity::class.java)
        startActivity(intent)
    }

    private fun openActivityStartActivity() {
        val intent = Intent(this, PlayersActivity::class.java)
        startActivity(intent)
    }

    private inner class getPlayers : AsyncTask<Void, Void, Unit>() {
        override fun doInBackground(vararg params: Void?) {
            players = db.playerDAO().showAllPlayers()

        }

    }
}