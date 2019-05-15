package  com.example.pv239_game_trio.frontend.games.hangman


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.games.hangman.HangmanGameActivity


class HangmanActivity : AppCompatActivity() {

    private lateinit var playBtn: Button
    private lateinit var spinner: Spinner
    private var playerCount: Int = 2

    private val TAG = "GameTrioHangman"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hangman)

        initSpinner()
        initButton()

        Log.d(TAG, "HangmanActivity created")
    }

    private fun initButton() {
        playBtn = findViewById<View>(R.id.playBtn) as Button
        playBtn.setOnClickListener{
            Log.d(TAG, "button PLAY HANGMAN was pressed")
            openHangmanGameActivity()
        }
    }

    private fun initSpinner() {
        spinner = this.findViewById(R.id.spinner)
        spinner.adapter = ArrayAdapter.createFromResource(this, R.array.people_count,android.R.layout.simple_spinner_item)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                playerCount = 2
            }

            override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
                playerCount = spinner.selectedItem.toString().toInt()
            }
        }
    }

    private fun openHangmanGameActivity() {
        val intent = Intent(this, HangmanGameActivity::class.java)
        intent.putExtra("playerCount", playerCount)
        startActivity(intent)
    }

}
