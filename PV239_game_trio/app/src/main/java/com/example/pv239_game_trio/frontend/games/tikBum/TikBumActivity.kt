package com.example.pv239_game_trio.frontend.games.tikBum

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NavUtils
import androidx.room.Room
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB


class TikBumActivity : AppCompatActivity() {

    private val TAG = "GameTrioTikBum"

    private val NUMBER_OF_WORDS_DB = 6
    private val TIME_MILLIS_UP_LIMIT : Long = 12000
    private val TIME_MILLIS_DOWN_LIMIT : Long = 1000

    private lateinit var soundTikTok : MediaPlayer
    private lateinit var soundBoom : MediaPlayer

    private var isBackDisable = true

    private lateinit var buttonStart : Button

    private lateinit var timer: CountDownTimer

    private lateinit var wordTextView: TextView
    private lateinit var positionTextView: TextView

    private lateinit var db : AppDB

    private var timerRunning : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tik_bum)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        Log.d(TAG,"TikBumActivity created")
        db = Room.databaseBuilder<AppDB>(applicationContext, AppDB :: class.java, "GameTrioDB").build()


        soundTikTok = MediaPlayer.create(this,R.raw.ticking_sound)
        soundBoom = MediaPlayer.create(this,R.raw.boom)

        buttonStart = findViewById(R.id.button_start_tik_bum)

        wordTextView = findViewById(R.id.TextView_TikTak)
        positionTextView = findViewById(R.id.TextView_position)


        buttonStart.setOnClickListener({
            Log.d(TAG,"button buttonStart was pressed")
            val randomTime = (TIME_MILLIS_DOWN_LIMIT..TIME_MILLIS_UP_LIMIT).shuffled().first()
            val randomWord = (1..7).shuffled().first()
            Log.d(TAG,"Time is set to: " + randomTime + " millis")
            positionTextView.text = getPosition().toUpperCase()

            Thread{
                val tikBumWord = db.tikBumDAO().findWordById(randomWord)
                wordTextView.text = tikBumWord.word.toUpperCase()
            }.start()


            startTimer(randomTime)

        })



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.upper_bar_ingame, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
            R.id.action_help -> {
                showHelp()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showHelp() {
        val helpBuild = AlertDialog.Builder(this)

        helpBuild.setTitle("Help")
        helpBuild.setMessage(
            "Guess the word by selecting the letters.\n\n" +
                    "You only have 6 wrong selections then it's game over!\n\n" +
                    "+5 points for guessing correct word\n" +
                    "+1 point for guessing correct letter\n" +
                    "-5 points for guessing wrong word\n" +
                    "-1 point for guessing wrong letter\n"
        )
        helpBuild.setPositiveButton(
            "OK"
        ) { _, _ -> }

        helpBuild.create()
        helpBuild.show()
    }

    private fun getPosition() : String{
        val positionString: MutableList<String> = mutableListOf<String>("Tick","TickTock","Boom")
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
                timerRunning = false
                buttonStart.visibility = View.VISIBLE
                soundTikTok.stop()
                soundBoom.start()
                openActivityAddPoints()

            }
        }
        timer.start()
    }



    private fun openActivityAddPoints(){
        val intent = Intent(this, TikBumAddPointsActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (isBackDisable) return
        Log.e(TAG, "Back is disabled")
    }


}
