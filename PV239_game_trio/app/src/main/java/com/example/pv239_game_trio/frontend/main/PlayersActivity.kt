package com.example.pv239_game_trio.frontend.main

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.DrawableCompat.setTint
import androidx.room.Room
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB
import com.example.pv239_game_trio.backend.entities.PlayerEntity
import com.example.pv239_game_trio.frontend.main.start.AddPlayerActivity

class PlayersActivity : AppCompatActivity() {
    private lateinit var listView : ListView
    private lateinit var db : AppDB
    private lateinit var players: Array<PlayerEntity>
    private lateinit var addPlayerButton: CardView
    private lateinit var startButton: CardView
    private lateinit var imageViewAdd: ImageView



    private val TAG = "GameTrioPlayersActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)
        db = Room.databaseBuilder<AppDB>(applicationContext, AppDB::class.java, "GameTrioDB").build()

        getPlayers().execute()
        listView = findViewById(R.id.players_list_view)
        imageViewAdd = findViewById(R.id.imageViewAdd)
        addPlayerButton = findViewById(R.id.buttonAddPlayer)
        startButton = findViewById(R.id.start_game)



        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedPlayer = players[position]
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want delete " + selectedPlayer.name  + "?")
            builder.setPositiveButton("Yes") { _, _ ->
                Thread {
                    db.playerDAO().delete(selectedPlayer)
                    Log.d(TAG, "player deleted")
                }.start()
                openActivityPlayer()
            }
            builder.setNegativeButton("No") { _, _ ->
                Log.d(TAG, "deleteAllPlayers() canceled by user")

            }
            builder.show()

        }

        addPlayerButton.setOnClickListener {
            if (players.size < 6){
                openActivityAdd()
            }
            else{
                Toast.makeText(this, "Max number of players is 6.", Toast.LENGTH_SHORT).show()
            }
        }
        startButton.setOnClickListener {
            if (players.size >= 2){
                openActivityChoose()
            }
            else{
                Toast.makeText(this, "There must be at least 2 players added.", Toast.LENGTH_SHORT).show()
            }
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

    private fun displayPlayers(){

        val listItems = arrayOfNulls<String>(players.size)

        for (i in 0 until players.size) {
            val player = players[i]
            listItems[i] = player.name
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listView.adapter = adapter
    }

    private fun openActivityPlayer(){
        val intent = Intent(this, PlayersActivity::class.java)
        startActivity(intent)
    }
    private fun openActivityAdd(){
        val intent = Intent(this, AddPlayerActivity::class.java)
        startActivity(intent)
    }
    private fun openActivityChoose(){
        val intent = Intent(this, ChooseActivity::class.java)
        startActivity(intent)
    }
}
