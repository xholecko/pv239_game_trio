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
    private val TAG = "GameTrioHangman"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hangman_menu)    
        Log.d(TAG,"HangmanActivity created")
    }

   

}
