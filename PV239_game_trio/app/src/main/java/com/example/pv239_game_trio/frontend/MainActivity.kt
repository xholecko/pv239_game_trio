package com.example.pv239_game_trio.frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.room.Room
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB
import com.example.pv239_game_trio.backend.entities.PlayerEntity
import com.example.pv239_game_trio.frontend.games.charade.CharadeActivity
import com.example.pv239_game_trio.frontend.games.hangman.HangmanActivity
import com.example.pv239_game_trio.frontend.games.tikBum.TikBumActivity

class MainActivity : AppCompatActivity() {

    private val TAG = "GameTrio"

    private lateinit var  buttonCharade: Button
    private lateinit var  buttonHangman: Button
    private lateinit var  buttonTikBum: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonCharade = findViewById(R.id.charade)
        buttonHangman = findViewById(R.id.hangman)
        buttonTikBum = findViewById(R.id.tikBum)


        buttonCharade.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button CHARADE was pressed")
            openActivityCharade()
        })


        buttonHangman.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button HANGMAN was pressed")
            openActivityHangman()
        })

        buttonTikBum.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button TIKBUM was pressed")
            openActivityTikBum()
        })

    }

    private fun openActivityCharade(){
        val intent = Intent(this, CharadeActivity::class.java)
        startActivity(intent)
    }

    private fun openActivityHangman(){
        val intent = Intent(this, HangmanActivity::class.java)
        startActivity(intent)
    }

    private fun openActivityTikBum(){
        val intent = Intent(this, TikBumActivity::class.java)
        startActivity(intent)
    }
}
