package  com.example.pv239_game_trio.games.hangman


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.pv239_game_trio.R


class HangmanActivity : AppCompatActivity() {

    private lateinit var playBtn:Button
    private lateinit var spinner: Spinner

    private val TAG = "GameTrioHangman"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hangman_menu)
        spinner = findViewById<View>(R.id.spinner) as Spinner
//        spinner.setOnScrollChangeListener{
//            Log.d(TAG,"button PLAY HANGMAN was pressed")
//        }
        playBtn = findViewById<View>(R.id.playBtn) as Button
        playBtn.setOnClickListener {
            Log.d(TAG,"button PLAY HANGMAN was pressed")
            openHangmanGameActivity()
        }
        Log.d(TAG,"HangmanActivity created")
    }

    private fun openHangmanGameActivity() {
        val intent = Intent(this, HangmanGameActivity::class.java)
        startActivity(intent)
    }

}
