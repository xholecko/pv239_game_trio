package com.example.pv239_game_trio.frontend.games.tikBum

import android.content.Intent
import android.media.MediaPlayer
import android.os.AsyncTask
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
import com.example.pv239_game_trio.backend.entities.PlayerEntity
import com.example.pv239_game_trio.backend.entities.TikBumEntity


class TikBumActivity : AppCompatActivity() {

    private val TAG = "GameTrioTikBum"

    private val TIME_MILLIS_UP_LIMIT : Long = 60000
    private val TIME_MILLIS_DOWN_LIMIT : Long = 1000
    private lateinit var syllables: Array<TikBumEntity>

    private lateinit var soundTikTok : MediaPlayer
    private lateinit var soundBoom : MediaPlayer

    private var isBackDisable = true

    private lateinit var buttonStart : Button

    private lateinit var timer: CountDownTimer

    private lateinit var wordTextView: TextView
    private lateinit var positionTextView: TextView

    private lateinit var db : AppDB

    private var timerRunning : Boolean = false


    private var tikBumWord : TikBumEntity = TikBumEntity()

    private var randomTime : Long = 0

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

        getPlayerCountAsync().execute()


        buttonStart.setOnClickListener({
            Log.d(TAG,"button buttonStart was pressed")
            randomTime = (TIME_MILLIS_DOWN_LIMIT..TIME_MILLIS_UP_LIMIT).shuffled().first()
            wordTextView.text = tikBumWord.word.toUpperCase()
            positionTextView.text = getPosition().toUpperCase()

            Log.d(TAG,"Time is set to: " + randomTime/1000 + " seconds")


            startTimer(randomTime)

        })



    }

    private inner class getPlayerCountAsync: AsyncTask<Void, Void, Unit>() {
        override fun doInBackground(vararg params: Void?) {
            syllables = db.tikBumDAO().showAllSyllables()

            val randomWord = (1..syllables.size).shuffled().first()
            //positionTextView.text = getPosition().toUpperCase()
            tikBumWord = db.tikBumDAO().findWordById(randomWord)

        }


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.upper_bar_ingame, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (timerRunning) {
                    timer.cancel()
                    soundTikTok.stop()
                    Log.e(TAG, "Back is pressed in toolbar")
                    Log.e(TAG, "timer is stopped, sound is stopped")
                }
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
            "Welcome to TIK BUM GAME.\n\n" +
                    "Random syllable will be generated with random position! " +
                    "Position can be suffix, prefix or infix.\n" +
                    "Your goal is to say the world that contain syllable in selected position. " +
                    "If you say the correct word it is the next player turn. \n" +

                    "But pay attention because time is ticking and the bomb may explode at random time! " +
                    "When the bomb explode the player who is currently on turn looses the game and other players gain one point each. \n\n" +
                    "Good luck!"


        )
        helpBuild.setPositiveButton(
            "OK"
        ) { _, _ -> }

        helpBuild.create()
        helpBuild.show()
    }

    private fun getPosition() : String{
        val positionString: MutableList<String> = mutableListOf("Prefix","Suffix","Infix")
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
        if (timerRunning){
            timer.cancel()
            soundTikTok.stop()
            Log.e(TAG, "Back is pressed")

            Log.e(TAG, "timer is stopped, sound is stopped")

        }

        super.onBackPressed()
        Log.e(TAG, "Back is pressed")

    }


}
