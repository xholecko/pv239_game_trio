package com.example.pv239_game_trio.frontend.games.charade

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.room.Room
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB

class CharadeAddPointsActivity : AppCompatActivity() {

    private val TAG = "GameTrioAddPoints"

    private lateinit var checkboxLayout: LinearLayout

    private lateinit var charadeText: TextView
    private lateinit var charadeType: TextView

    private lateinit var checkbox1: CheckBox
    private lateinit var checkbox2: CheckBox
    private lateinit var checkbox3: CheckBox
    private lateinit var checkbox4: CheckBox
    private lateinit var checkbox5: CheckBox
    private lateinit var checkbox6: CheckBox

    private lateinit var textView: TextView
    private lateinit var buttonAddPoints: Button
    private lateinit var buttonNewCharade: Button

    private lateinit var db : AppDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charade_add_points)
        Log.d(TAG,"onCreate()")

        db = Room.databaseBuilder<AppDB>(applicationContext, AppDB :: class.java, "GameTrioDB").build()

        checkboxLayout = findViewById(R.id.checkboxLayout)
        textView = findViewById(R.id.text_view_selected)
        buttonAddPoints = findViewById(R.id.button_confirm)
        buttonNewCharade = findViewById(R.id.button_new_charade)
        checkbox1 = findViewById(R.id.checkbox_one)
        checkbox2 = findViewById(R.id.checkbox_two)
        checkbox3 = findViewById(R.id.checkbox_three)
        checkbox4 = findViewById(R.id.checkbox_four)
        checkbox5 = findViewById(R.id.checkbox_five)
        checkbox6 = findViewById(R.id.checkbox_six)
        textView.text = getString(R.string.winners)

        charadeText = findViewById(R.id.text_charade_word)
        charadeText.text = intent.getStringExtra("CharadeText")
        charadeType = findViewById(R.id.text_charade_type)
        charadeType.text = intent.getStringExtra("CharadeType")

        val numbers = mutableListOf(checkbox1,checkbox2,checkbox3,checkbox4,checkbox5,checkbox6)

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
            var selected = getCheckedCheckboxes(numbers)
            if(selected.size != 2) {
                Toast.makeText(this, "Wrong number of players selected! Select 2.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Thread{
                    for (i in 0 .. 5){
                        if (numbers[i].isChecked){
                            db.playerDAO().addPointsById(db.playerDAO().showAllPlayers()[i].id,1)
                        }
                    }
                }.start()
                hideSaveButton()
            }
        })

        buttonNewCharade.setOnClickListener(View.OnClickListener {
            openActivityCharade()
        })

    }

    private fun hideSaveButton() {
        buttonAddPoints.visibility = View.INVISIBLE
    }

    private fun getCheckedCheckboxes(allCheckboxes : List<CheckBox>): List<CheckBox> {
        var checked = mutableListOf<CheckBox>()
        for (i in 0 .. 5){
            if (allCheckboxes[i].isChecked){
                checked.add(allCheckboxes[i])
            }

        }
        return checked
    }

    private fun openActivityCharade(){
        val intent = Intent(this, CharadeActivity::class.java)
        startActivity(intent)
    }

}
