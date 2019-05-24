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


    private var timerRunning : Boolean = false

    //private val timeLeftInMillis : Long = TIME_MILLIS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tik_bum)
        Log.d(TAG,"TikBumActivity created")
        val db = Room.databaseBuilder<AppDB>(applicationContext, AppDB :: class.java, "GameTrioDB").build()
        createWords(db)
        createPlayers(db)
        //someTestDeleteLater(db)


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



                //TODO presmerovat do fragmentu hracovi dat dole body

            }
        }
        timer.start()
    }

    private fun createWords(db: AppDB){
        Thread {
            var word0 = TikBumEntity()
            word0.id = 0
            word0.word = "lol"

            var word1 = TikBumEntity()
            word1.id = 1
            word1.word = "ha"

            var word2 = TikBumEntity()
            word2.id = 2
            word2.word = "na"

            var word3 = TikBumEntity()
            word3.id = 3
            word3.word = "ba"

            var word4 = TikBumEntity()
            word4.id = 4
            word4.word = "fa"

            var word5 = TikBumEntity()
            word5.id = 5
            word5.word = "ra"

            var word6 = TikBumEntity()
            word6.id = 6
            word6.word = "la"

            db.tikBumDAO().create(word0)
            db.tikBumDAO().create(word1)
            db.tikBumDAO().create(word2)
            db.tikBumDAO().create(word3)
            db.tikBumDAO().create(word4)
            db.tikBumDAO().create(word5)
            db.tikBumDAO().create(word6)
            Log.d(TAG,"7 words created")

        }.start()
    }


    private fun createPlayers(db: AppDB){
        Thread {
            var player1 = PlayerEntity()
            player1.id = 0
            player1.name = "Peter"
            player1.points = 0


            var player2 = PlayerEntity()
            player2.id = 1
            player2.name = "Marcel"
            player2.points = 0


            var player3 = PlayerEntity()
            player3.id = 2
            player3.name = "Jitka"
            player3.points = 0


            var player4 = PlayerEntity()
            player4.id = 3
            player4.name = "Sara"
            player4.points = 0



            db.playerDAO().create(player1)
            db.playerDAO().create(player2)
            db.playerDAO().create(player3)
            db.playerDAO().create(player4)

            Log.d(TAG,"4 players created")

        }.start()
    }
}
