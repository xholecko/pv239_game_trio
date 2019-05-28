package com.example.pv239_game_trio.frontend.main.start

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


class RemovePlayerActivity : AppCompatActivity() {
    private val TAG = "GameTrioRemovePlayer"

    private lateinit var  radioGroup: RadioGroup
    private lateinit var  radioButton: RadioButton
    private lateinit var  textView: TextView
    private lateinit var  buttonApply: Button

    private lateinit var db : AppDB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove_player)
        Log.d(TAG,"onCreate()")

        db = Room.databaseBuilder<AppDB>(applicationContext, AppDB :: class.java, "GameTrioDB").build()

        radioGroup = findViewById(R.id.radioGroup)
        textView = findViewById(R.id.text_view_selected)
        buttonApply = findViewById(R.id.button_apply)

        buttonApply.setOnClickListener(View.OnClickListener {
            val radioId = radioGroup.checkedRadioButtonId
            radioButton = findViewById(radioId)

        })


    }

    //https://www.youtube.com/watch?v=fwSJ1OkK304
    fun checkButton(v: View) {
        val radioId = radioGroup.checkedRadioButtonId

        radioButton = findViewById(radioId)


    }
}
