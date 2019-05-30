package com.example.pv239_game_trio.frontend.main.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB
import com.example.pv239_game_trio.backend.entities.PlayerEntity
import com.example.pv239_game_trio.frontend.main.PlayersActivity
import com.google.android.material.textfield.TextInputLayout

class AddPlayerActivity : AppCompatActivity() {

    private val TAG = "GameTrioAddPlayer"
    private lateinit var  addPlayerButton: Button
    //private lateinit var  playersTextView: TextView
    //lateinit var mRecyclerView: RecyclerView
    lateinit var textInputWord: TextInputLayout
    private var playersInDb : Int = 0

    private lateinit var db : AppDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_player_form)
        Log.d(TAG,"onCreate()")

        db = Room.databaseBuilder<AppDB>(applicationContext, AppDB :: class.java, "GameTrioDB").build()


        Thread{
            playersInDb = db.playerDAO().showAllPlayers().size
        }.start()

        Log.d(TAG, "Players in DB = " + playersInDb)

        textInputWord = findViewById(R.id.input)

        addPlayerButton = findViewById(R.id.button_add_player)


        addPlayerButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button AddPlayer was pressed")

            var name = textInputWord.editText!!.text.toString().toUpperCase().trim()

            if (validateInput(name)){

                var dbValidation = "ok"

                Log.d(TAG,"Adding player to DB with name: " + name)

                Thread{


                    if (db.playerDAO().findPlayerByName(name) != null){
                        Log.d(TAG,"There is player with the same name already")
                        dbValidation = "nameProblem"
                    }

                    if (playersInDb >= 6){
                        Log.d(TAG,"Max number of players is 6 and you currently have " + playersInDb)
                        dbValidation = "sizeProblem"
                    }

                    if (dbValidation == "ok"){
                        var player = PlayerEntity()
                        //player.id = playersInDb + 1
                        player.name = name
                        player.points = 0
                        db.playerDAO().create(player)

                        Log.d(TAG,"Player was created")
                        val a = db.playerDAO().findPlayerByName(player.name)
                        Log.d(TAG,"Name= " + a.name + " Points= "+  a.points + " Id= " + a.id)
                        playersInDb += 1
                        Log.d(TAG,"number of players in DB is : " + playersInDb)
                        //getAllPlayers(db)

                    }

                }.start()
                Toast.makeText(this, "Player " + name + " created", Toast.LENGTH_SHORT).show()

                openActivityMain()


                if (dbValidation == "sizeProblem"){
                        textInputWord.error = "Max number of players is 6"
                    }


                if (dbValidation == "nameProblem") {
                        textInputWord.error = "There is already player with the same name"
                    }


            } else{
                Toast.makeText(this, "Validation problem", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun validateInput(name: String): Boolean {
        Log.d(TAG,"Validation started")
        if (name.isEmpty()){
            textInputWord.error = "Field can not be empty"
            Log.d(TAG,"Validation not succesful")
            return false
        }
        if (name.length > 20){
            textInputWord.error = "Name can not have more than 20 characters"
            Log.d(TAG,"Validation not succesful")
            return false
        }
        else {
            textInputWord.error = null
            Log.d(TAG,"Validation succesful")
            return true
        }

    }

    private fun openActivityMain(){
        val intent = Intent(this, PlayersActivity::class.java)
        startActivity(intent)
    }
}
