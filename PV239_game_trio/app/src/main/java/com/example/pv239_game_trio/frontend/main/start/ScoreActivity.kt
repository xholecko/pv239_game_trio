package com.example.pv239_game_trio.frontend.main.start

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NavUtils
import androidx.room.Room
import com.example.pv239_game_trio.backend.entities.PlayerEntity

class ScoreActivity : AppCompatActivity() {

    private lateinit var db : AppDB
    private lateinit var players: Array<PlayerEntity>
    private lateinit var sortedPlayers: List<PlayerEntity>
    private lateinit var table: TableLayout

    private val TAG = "Score"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        db = Room.databaseBuilder<AppDB>(applicationContext, AppDB :: class.java, "GameTrioDB").build()

        table = findViewById(R.id.table_score)

        updateTable().execute()
    }

    private inner class updateTable: AsyncTask<Void, Void, Unit>() {
        override fun doInBackground(vararg params: Void?) {
            players = db.playerDAO().showAllPlayers()
            sortedPlayers = players.sortedWith(compareBy {it.points})
        }

        override fun onPostExecute(result: Unit?) {
            updateTableRows()
        }
    }

    private fun updateTableRows() {
        for(i in 0 until table.childCount) {
            if (sortedPlayers.size == i) {
                break
            }

            var row: TableRow = table.getChildAt(i) as TableRow
            var player = sortedPlayers[sortedPlayers.size - 1 - i]

            var playerName: TextView = row.getChildAt(0) as TextView
            playerName.text = player.name

            var playerPoints: TextView = row.getChildAt(1) as TextView
            playerPoints.text = player.points.toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.upper_bar_score_reset, menu)
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
                    openActivityScore()
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
                    }.start()
                    openActivityMain()

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
    private fun openActivityMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
