package com.example.pv239_game_trio.frontend.games.hangman

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pv239_game_trio.R

class HangmanActivity : AppCompatActivity() {
    private val TAG = "GameTrioHangman"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hangman_menu)
        Log.d(TAG,"HangmanActivity created")
    }
}
