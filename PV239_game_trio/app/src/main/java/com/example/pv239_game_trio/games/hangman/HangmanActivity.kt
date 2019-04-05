package com.example.pv239_game_trio.games.hangman

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pv239_game_trio.R

class HangmanActivity : AppCompatActivity() {

    private val TAG = "GameTrioHangman"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hangman)
    }
}
