package com.example.pv239_game_trio.frontend.main.start

import android.app.AlertDialog
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.NavUtils
import androidx.room.Room
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB
import com.example.pv239_game_trio.backend.entities.PlayerEntity
import com.example.pv239_game_trio.frontend.main.ChooseActivity

class MainActivity : AppCompatActivity() {


    private val TAG = "GameTrioMainActivity"

    private lateinit var  startActivityButton: CardView
    private lateinit var  addPlayerButton: CardView
    private lateinit var  removePlayerButton: CardView
    private lateinit var  buttonScore: CardView
    private lateinit var  playersTextView: TextView
    private lateinit var db : AppDB
    private var playersInDb : Int = 0
    private lateinit var players: Array<PlayerEntity>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate()")

        db = Room.databaseBuilder<AppDB>(applicationContext, AppDB::class.java, "GameTrioDB").build()

        addPlayerButton = findViewById(R.id.cardView_add)

        startActivityButton = findViewById(R.id.cardView_start)
        removePlayerButton = findViewById(R.id.cardView_remove_player)
        buttonScore = findViewById(R.id.cardView_score)
        playersTextView = findViewById(R.id.textView_players)

        getPlayers().execute()

        addPlayerButton.setOnClickListener {
            Log.d(TAG, "button ADD PLAYER was pressed")
            openActivityAddPlayerActivity()
        }

        startActivityButton.setOnClickListener {
            Log.d(TAG, "button START was pressed")
            openActivityStartActivity()
        }

        removePlayerButton.setOnClickListener {
            Log.d(TAG, "button REMOVE PLAYER was pressed")
            openActivityRemovePlayerActivity()
        }
        buttonScore.setOnClickListener {
            Log.d(TAG,"button TIKBUM was pressed")
            openActivityScore()
        }
    }

    private fun displayPlayers() {
        playersTextView.text = getAllPlayers(players)
        if (players.size >= 6) {
            addPlayerButton.isEnabled = false
            //TODO
            //val image: ImageView = findViewById(R.id.imageStart)
            //image.
        }
        if (players.isEmpty()) {
            removePlayerButton.isEnabled = false
        }
        if (players.size < 2){
            startActivityButton.isEnabled = false
        }
    }

    private inner class getPlayers : AsyncTask<Void, Void, Unit>() {
        override fun doInBackground(vararg params: Void?) {
            players = db.playerDAO().showAllPlayers()

        }

        override fun onPostExecute(result: Unit?) {
            displayPlayers()
        }
    }

    private fun openActivityAddPlayerActivity() {
        val intent = Intent(this, AddPlayerActivity::class.java)
        startActivity(intent)
    }

    private fun openActivityStartActivity() {
        val intent = Intent(this, ChooseActivity::class.java)
        startActivity(intent)
    }

    private fun openActivityRemovePlayerActivity() {
        val intent = Intent(this, RemovePlayerActivity::class.java)
        startActivity(intent)
    }

    private fun openActivityMain() {
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }


    private fun getAllPlayers(players: Array<PlayerEntity>): String {
        var output = ""

        playersInDb = players.size
        Log.d(TAG, "Players in DB = $playersInDb")


        for(i in 0 until playersInDb){
            output = output + players[i].name + "\n"//" points = " + db.playerDAO().showAllPlayers()[i].points + "\n"
            Log.d(TAG, "Name= " + players[i].name + " Id= " + players[i].id)


        }

        return output
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.upper_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }

            R.id.reset_points -> {
                Log.d(TAG, "button RESET POINTS was pressed")

                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setMessage("Are you sure you want to reset all points?")
                builder.setPositiveButton("Yes") { _, _ ->
                    Thread {
                        db.playerDAO().resetPointAllPlayers()
                        Log.d(TAG, "resetPointAllTeams() DONE")
                    }.start()
                }
                builder.setNegativeButton("No") { _, _ ->
                    Log.d(TAG, "resetPointAllTeams() canceled by user")

                }
                builder.show()
            }

            R.id.restart_game -> {
                Log.d(TAG, "button RESTART GAME was pressed")

                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setMessage("Are you sure you want to restart game?")
                builder.setPositiveButton("Yes") { _, _ ->
                    Thread {
                        db.playerDAO().deleteAllPlayers()
                        Log.d(TAG, "deleteAllPlayers() DONE")
                        openActivityMain()
                    }.start()
                }
                builder.setNegativeButton("No") { _, _ ->
                    Log.d(TAG, "deleteAllPlayers() canceled by user")

                }
                builder.show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun openActivityScore(){
        val intent = Intent(this, ScoreActivity::class.java)
        startActivity(intent)
    }
}
