package com.example.pv239_game_trio.frontend.main.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.room.Room
import com.example.pv239_game_trio.backend.entities.PlayerEntity

class ScoreActivity : AppCompatActivity() {

    private lateinit var db : AppDB
    private lateinit var players: Array<PlayerEntity>
    private lateinit var sortedPlayers: List<PlayerEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        db = Room.databaseBuilder<AppDB>(applicationContext, AppDB :: class.java, "GameTrioDB").build()

        var table: TableLayout = findViewById(R.id.table_score)

        Thread {
            players = db.playerDAO().showAllPlayers()
            sortedPlayers = players.sortedWith(compareBy {it.points})
        }.start()

        for(i in 0 until table.childCount) {
            if (sortedPlayers.size < i - 1) {
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
}
