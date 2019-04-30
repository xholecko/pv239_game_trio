package com.example.pv239_game_trio.games.tikBum

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pv239_game_trio.R

class TikBumActivity : AppCompatActivity() {

    private val TAG = "GameTrioTikBum"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tik_bum)

        Log.d(TAG,"TikBumActivity created")

    }
}
