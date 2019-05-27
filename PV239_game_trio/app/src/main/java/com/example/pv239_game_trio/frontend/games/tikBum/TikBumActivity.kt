package com.example.pv239_game_trio.frontend.games.tikBum

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.room.Room
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB
import com.example.pv239_game_trio.backend.entities.PlayerEntity
import com.example.pv239_game_trio.backend.entities.TikBumEntity

class TikBumActivity : AppCompatActivity() {

    private val TAG = "GameTrioTikBum"

    private val NUMBER_OF_WORDS_DB = 6
    private val TIME_MILLIS_UP_LIMIT : Long = 12000
    private val TIME_MILLIS_DOWN_LIMIT : Long = 1000

    private lateinit var soundTikTok : MediaPlayer
    private lateinit var soundBoom : MediaPlayer


    private lateinit var buttonStart : Button

    private lateinit var timer: CountDownTimer

    private lateinit var wordTextView: TextView
    private lateinit var positionTextView: TextView

    private lateinit var db : AppDB

    private var timerRunning : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tik_bum)
        Log.d(TAG,"TikBumActivity created")
        db = Room.databaseBuilder<AppDB>(applicationContext, AppDB :: class.java, "GameTrioDB").build()


        soundTikTok = MediaPlayer.create(this,R.raw.ticking_sound)
        soundBoom = MediaPlayer.create(this,R.raw.boom)

        buttonStart = findViewById(R.id.button_start_tik_bum)

        wordTextView = findViewById(R.id.TextView_TikTak)
        positionTextView = findViewById(R.id.TextView_position)


        //TODO
        buttonStart.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button buttonStart was pressed")
            val randomTime = (TIME_MILLIS_DOWN_LIMIT..TIME_MILLIS_UP_LIMIT).shuffled().first()
            val randomWord = (0..6).shuffled().first()
            Log.d(TAG,"Time is set to: " + randomTime + " millis")
            positionTextView.text = getPosition().toUpperCase()



            Thread{
                var tikBumWord = db.tikBumDAO().findWordById(randomWord)
                wordTextView.text = tikBumWord.word.toUpperCase()
            }.start()


            startTimer(randomTime)

        })



    }

    private fun getPosition() : String{
        var positionString: MutableList<String> = mutableListOf<String>("Tick","TickTock","Boom")
        return positionString[(0..2).shuffled().first()]

    }

    private fun startTimer(timeLeft : Long){
        timer = object: CountDownTimer(timeLeft,1000) {
            override fun onTick(millisUntilFinished: Long) {
                buttonStart.visibility = View.INVISIBLE
                timerRunning = true
                //soundTikTok.isLooping = true
                soundTikTok.start()
            }

            override fun onFinish() {
                buttonStart.visibility = View.VISIBLE
                timerRunning = false
                //soundTikTok.isLooping = false
                soundTikTok.stop()
                soundBoom.start()




            }
        }
        timer.start()
    }

    //open in Thread
    private fun addPoints(id: Int){
        db.playerDAO().addPointsToAllButOneById(id,1)
    }


}
