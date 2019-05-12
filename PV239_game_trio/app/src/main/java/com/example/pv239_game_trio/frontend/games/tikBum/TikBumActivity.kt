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
    private val TIME_MILLIS_UP_LIMIT : Long = 6000
    private val TIME_MILLIS_DOWN_LIMIT : Long = 1000

    private lateinit var soundTikTok : MediaPlayer
    private lateinit var soundBoom : MediaPlayer


    lateinit var buttonStart : Button

    lateinit var timer: CountDownTimer

    lateinit var wordTextView: TextView

    private var timerRunning : Boolean = false

    //private val timeLeftInMillis : Long = TIME_MILLIS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tik_bum)
        Log.d(TAG,"TikBumActivity created")
        val db = Room.databaseBuilder<AppDB>(applicationContext, AppDB :: class.java, "GameTrioDB").build()
        createWords(db)
        //someTestDeleteLater(db)


        soundTikTok = MediaPlayer.create(this,R.raw.tick_tack)
        soundBoom = MediaPlayer.create(this,R.raw.boom)

        buttonStart = findViewById(R.id.button_start_tik_bum)

        wordTextView = findViewById(R.id.TextView_TikTak)


        //TODO
        buttonStart.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button buttonStart was pressed")
            val randomTime = (TIME_MILLIS_DOWN_LIMIT..TIME_MILLIS_UP_LIMIT).shuffled().first()
            val randomWord = (0..6).shuffled().first()
            val randomPosition = (0..2).shuffled().first()
            Log.d(TAG,"Time is set to: " + randomTime + " millis")

            Thread{
                var tikBumWord = db.tikBumDAO().findWordById(randomWord)
                wordTextView.text = tikBumWord.word.toUpperCase()

            }.start()


            startTimer(randomTime)
        })



    }


    private fun startTimer(timeLeft : Long){
        timer = object: CountDownTimer(timeLeft,1000) {
            override fun onTick(millisUntilFinished: Long) {
                buttonStart.visibility = View.INVISIBLE
                timerRunning = true
                soundTikTok.start()
                soundTikTok.isLooping = true
            }

            override fun onFinish() {
                buttonStart.visibility = View.VISIBLE
                timerRunning = false
                soundTikTok.isLooping = false
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






    private fun someTestDeleteLater(db: AppDB){
        /// SOME DB TESTING, WILL DELETE LATER WHEN PROPER TESTS ARE DONE
        Thread {


            Log.d(TAG,"1")

            //db.playerDAO().deleteAllPlayers()
            //db.tikBumDAO().deleteAllWords()
            Log.d(TAG,"2")

            var player1 = PlayerEntity()
            player1.id = 16
            player1.name = "Peter"
            player1.points = 1

            var player2 = PlayerEntity()
            player2.id = 24
            player2.name = "Martin"
            player2.points = 2
            Log.d(TAG,"4")

            db.playerDAO().create(player1)
            Log.d(TAG,"9")

            db.playerDAO().create(player2)
            Log.d(TAG,"8")


            db.playerDAO().showAllPlayers().forEach {
                Log.d(TAG,"Player 2* : " + it.id + " " + it.name + " " + it.points)

            }


            db.playerDAO().resetPointAllPlayers()
            db.playerDAO().showAllPlayers().forEach {
                Log.d(TAG,"Zero points all* : " + it.id + " " + it.name + " " + it.points)

            }


            db.playerDAO().deleteAllPlayers()

            db.playerDAO().create(player1)



            player1.name = "noveMeno"
            db.playerDAO().update(player1)

            db.playerDAO().showAllPlayers().forEach {
                Log.d(TAG,"NewName : " + it.id + " " + it.name + " " + it.points)

            }

            db.playerDAO().create(player2)

            var foundPlayerId = db.playerDAO().findPlayerById(player2.id)
            Log.d(TAG,"Founded player ID : " + foundPlayerId.id + " " + foundPlayerId.name + " " + foundPlayerId.points)

            var foundPlayerName = db.playerDAO().findPlayerByName(player2.name)
            Log.d(TAG,"Founded player Name : " + foundPlayerName.id + " " + foundPlayerName.name + " " + foundPlayerName.points)

            var points = db.playerDAO().getPointsById(player2.id)
            Log.d(TAG,"points : " + points)

            db.playerDAO().addPointsById(player2.id,-155)
            points = db.playerDAO().getPointsById(player2.id)
            Log.d(TAG,"points : " + points)

            db.playerDAO().delete(player2)

            db.playerDAO().showAllPlayers().forEach {
                Log.d(TAG,"Player 1* : " + it.id + " " + it.name + " " + it.points)
            }
            var word1 = TikBumEntity()

            word1.id = 8
            word1.word = "ha"

            db.tikBumDAO().create(word1)


            db.tikBumDAO().showAllWords().forEach {
                Log.d(TAG,"Word : " + it.id + " " + it.word)
            }



        }.start()

    }
}
