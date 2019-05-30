package com.example.pv239_game_trio.frontend.main.start

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.frontend.main.PlayersActivity

class MainActivity : AppCompatActivity() {


    private val TAG = "GameTrioMainActivity"

    private lateinit var playButton: Button
    private lateinit var showScoreButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate()")

        playButton = findViewById(R.id.play_button)
        showScoreButton = findViewById(R.id.show_score_button)

        playButton.setOnClickListener {
            Log.d(TAG, "button START was pressed")
            openActivityStartActivity()
        }

        showScoreButton.setOnClickListener {
            openScoreActivity()
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
}
