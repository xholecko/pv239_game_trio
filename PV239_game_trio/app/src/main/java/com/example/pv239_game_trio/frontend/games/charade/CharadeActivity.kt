package com.example.pv239_game_trio.frontend.games.charade

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB
import com.example.pv239_game_trio.backend.entities.CharadeEntity
import java.util.Random
import androidx.room.Room
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NavUtils

class CharadeActivity : AppCompatActivity() {

    private val TAG = "GameTrioCharade"

    private lateinit var db : AppDB
    private lateinit var random : Random

    private lateinit var timer: CountDownTimer
    private var timerRunning : Boolean = false

    private lateinit var buttonStart : Button
    private lateinit var charadeText: TextView
    private lateinit var charadeType: TextView
    private lateinit var countdown: TextView

    private lateinit var allCharades: List<CharadeEntity>
    private lateinit var generatedCharade: Pair<CharadeEntity, CharadeType>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charade)
        Log.d(TAG,"CharadeActivity created")

        db = Room.databaseBuilder(applicationContext, AppDB :: class.java, "GameTrioDB").build()
        random = Random()

        buttonStart = findViewById(R.id.button_start_charade)
        charadeText = findViewById(R.id.text_charade_word)
        charadeType = findViewById(R.id.text_charade_type)
        countdown = findViewById(R.id.charade_countdown)

        buttonStart.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"Button for starting charade was pressed")
            setTimer()
        })

        getCharade().execute()
    }

    private inner class getCharade: AsyncTask<Void, Void, Unit>() {
        override fun doInBackground(vararg params: Void?) {
            allCharades = db.charadeDAO().showAllCharades()
            generatedCharade = generateCharade()
        }

        override fun onPostExecute(result: Unit?) {
            updateToNewCharade()
        }
    }

    private fun updateToNewCharade() {
        charadeText.text = generatedCharade.first.text
        charadeType.text = generatedCharade.second.toString()
        countdown.text = "60"
    }

    /**
     * Generates charade with random activity type
     */
    private fun generateCharade(): Pair<CharadeEntity, CharadeType> {
        var charade = allCharades.get(random.nextInt(allCharades.size))
        var charadeType = CharadeType.values()[random.nextInt(CharadeType.values().size)]

        return Pair(charade, charadeType)
    }

    private fun setTimer(){
        timer = object: CountDownTimer(60000,1000) {

            override fun onTick(millisUntilFinished: Long) {
                buttonStart.visibility = View.INVISIBLE
                countdown.text = (millisUntilFinished / 1000).toString()
                timerRunning = true
            }

            override fun onFinish() {
                timerRunning = false
                openActivityAddPoints()
            }
        }
        timer.start()
    }

    private fun openActivityAddPoints(){
        val intent = Intent(this, CharadeAddPointsActivity::class.java)
        intent.putExtra("CharadeText",getCharadeTextAsString())
        intent.putExtra("CharadeType",getCharadeTypeAsString())
        startActivity(intent)
    }

    private fun getCharadeTextAsString(): String {
        return charadeText.text.toString()
    }

    private fun getCharadeTypeAsString(): String {
        return charadeType.text.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.upper_bar_ingame, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (timerRunning){
                    timer.cancel()
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

    override fun onBackPressed() {
        if (timerRunning){
            timer.cancel()
        }

        super.onBackPressed()
        Log.e(TAG, "Back is disabled")
    }
}
