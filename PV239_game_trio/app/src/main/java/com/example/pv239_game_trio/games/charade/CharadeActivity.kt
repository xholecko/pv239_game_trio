package com.example.pv239_game_trio.games.charade

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pv239_game_trio.R

class CharadeActivity : AppCompatActivity() {

    private val TAG = "GameTrioCharade"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charade)

        Log.d(TAG,"CharadeActivity created")

    }
}
