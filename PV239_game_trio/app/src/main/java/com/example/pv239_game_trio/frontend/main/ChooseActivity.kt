package com.example.pv239_game_trio.frontend.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.cardview.widget.CardView
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.frontend.games.charade.CharadeActivity
import com.example.pv239_game_trio.frontend.games.hangman.HangmanGameActivity
import com.example.pv239_game_trio.frontend.games.tikBum.TikBumActivity
import com.example.pv239_game_trio.frontend.main.start.ScoreActivity

class ChooseActivity : AppCompatActivity() {

    private val TAG = "GameTrioChoose"

    private lateinit var  buttonCharade: CardView
    private lateinit var  buttonHangman: CardView
    private lateinit var  buttonTikBum: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)

        buttonCharade = findViewById(R.id.charadeCardView)
        buttonHangman = findViewById(R.id.hangmanCardView)
        buttonTikBum = findViewById(R.id.tikBumCardView)


        buttonCharade.setOnClickListener {
            Log.d(TAG,"button CHARADE was pressed")
            openActivityCharade()
        }


        buttonHangman.setOnClickListener {
            Log.d(TAG,"button HANGMAN was pressed")
            openActivityHangman()
        }

        buttonTikBum.setOnClickListener {
            Log.d(TAG,"button TIKBUM was pressed")
            openActivityTikBum()
        }


    }

    private fun openActivityCharade(){
        val intent = Intent(this, CharadeActivity::class.java)
        startActivity(intent)
    }

    private fun openActivityHangman(){
        val intent = Intent(this, HangmanGameActivity::class.java)
        startActivity(intent)
    }

    private fun openActivityTikBum(){
        val intent = Intent(this, TikBumActivity::class.java)
        startActivity(intent)
    }

}
