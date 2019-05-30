package com.example.pv239_game_trio.frontend.main.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.RadioButton
import android.widget.RadioGroup

import android.view.View
import androidx.room.Room
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB
import kotlinx.android.synthetic.main.activity_remove_player.*





class RemovePlayerActivity : AppCompatActivity() {
    private val TAG = "GameTrioRemovePlayer"

    private lateinit var  radioGroup: RadioGroup
    private lateinit var  radioButton: RadioButton

    private lateinit var  radioButton1: RadioButton
    private lateinit var  radioButton2: RadioButton
    private lateinit var  radioButton3: RadioButton
    private lateinit var  radioButton4: RadioButton
    private lateinit var  radioButton5: RadioButton
    private lateinit var  radioButton6: RadioButton

    private lateinit var  textView: TextView
    private lateinit var  buttonRemove: Button
    private lateinit var  buttonCancel: Button


    private lateinit var db : AppDB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove_player)
        Log.d(TAG,"onCreate()")

        db = Room.databaseBuilder<AppDB>(applicationContext, AppDB :: class.java, "GameTrioDB").build()

        radioGroup = findViewById(R.id.radioGroup)
        textView = findViewById(R.id.text_view_selected)
        buttonRemove = findViewById(R.id.button_apply)
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

        buttonRemove.setOnClickListener(View.OnClickListener {
            val radioId = radioGroup.checkedRadioButtonId

            radioButton = findViewById(radioId)

            Thread{
                for (i in 0 .. 5){
                    if (numbers[i] == radioButton){
                        db.playerDAO().delete(db.playerDAO().showAllPlayers()[i])
                        openActivityMain()
                    }

                }
            }.start()
        })
        buttonCancel.setOnClickListener(View.OnClickListener {
            openActivityMain()
        })
    }

    //https://www.youtube.com/watch?v=fwSJ1OkK304
    fun checkButton(v: View) {
        val radioId = radioGroup.checkedRadioButtonId
        Log.d(TAG,radioId.toString())
        radioButton = findViewById(radioId)
    }


    private fun openActivityMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
