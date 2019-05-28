package com.example.pv239_game_trio.frontend.games.tikBum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.room.Room
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB
import com.example.pv239_game_trio.frontend.main.start.MainActivity

class TikBumAddPointsActivity : AppCompatActivity() {

    private val TAG = "GameTrioAddPoints"

    private lateinit var  radioGroup: RadioGroup
    private lateinit var  radioButton: RadioButton

    private lateinit var  radioButton1: RadioButton
    private lateinit var  radioButton2: RadioButton
    private lateinit var  radioButton3: RadioButton
    private lateinit var  radioButton4: RadioButton
    private lateinit var  radioButton5: RadioButton
    private lateinit var  radioButton6: RadioButton

    private lateinit var  textView: TextView
    private lateinit var  buttonAddPoints: Button


    private lateinit var db : AppDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tik_bum_add_points)
        Log.d(TAG,"onCreate()")

        db = Room.databaseBuilder<AppDB>(applicationContext, AppDB :: class.java, "GameTrioDB").build()

        radioGroup = findViewById(R.id.radioGroup)
        textView = findViewById(R.id.text_view_selected)
        buttonAddPoints = findViewById(R.id.button_confirm)
        radioButton1 = findViewById(R.id.radio_one)
        radioButton2 = findViewById(R.id.radio_two)
        radioButton3 = findViewById(R.id.radio_three)
        radioButton4 = findViewById(R.id.radio_four)
        radioButton5 = findViewById(R.id.radio_five)
        radioButton6 = findViewById(R.id.radio_six)

        val numbers = mutableListOf(radioButton1,radioButton2,radioButton3,radioButton4,radioButton5,radioButton6)

        Thread{
            val players = db.playerDAO().showAllPlayers()
            for(i in 0..players.size - 1){
                numbers[i].text = players[i].name
            }

            for (i in players.size .. 5){
                numbers[i].visibility = View.INVISIBLE
            }
        }.start()

        buttonAddPoints.setOnClickListener(View.OnClickListener {
            val radioId = radioGroup.checkedRadioButtonId

            radioButton = findViewById(radioId)

            Thread{
                for (i in 0 .. 5){
                    if (numbers[i] == radioButton){
                        db.playerDAO().addPointsToAllButOneById(db.playerDAO().showAllPlayers()[i].id,1)
                        openActivityTikBum()
                    }

                }
            }.start()
        })



    }
    private fun openActivityTikBum(){
        val intent = Intent(this, TikBumActivity::class.java)
        startActivity(intent)
    }
}
