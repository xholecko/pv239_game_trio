package com.example.pv239_game_trio.games.hangman

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.app.NavUtils
import com.example.pv239_game_trio.R
import java.util.*


class HangmanGameActivity : AppCompatActivity() {

    private lateinit var words: Array<String>
    private lateinit var currentWord: String
    private lateinit var wordLayout: LinearLayout
    private lateinit var charViews: Array<TextView>

    private lateinit var letters: GridView

    private lateinit var submitButton: Button
    private lateinit var currentPlayerTextView: TextView
    private lateinit var currentPlayerScoreTextView: TextView
    private lateinit var guess:EditText

    private lateinit var bodyParts: Array<ImageView>
    private val numParts = 6
    private var currentPart: Int = 0
    private var numChars: Int = 0
    private var numCorr: Int = 0
    private lateinit var scoreArray: Array<Int>
    private var playerCount:Int = 0
    private var currentPlayer = 0

    private val TAG = "HangmanGame"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hangman_game)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        initVariables()

        gameInit()

        Log.d(TAG, "HangmanGameActivity created")
    }

    private fun initVariables() {
        bodyParts = Array(numParts) { ImageView(this) }
        bodyParts[0] = findViewById(R.id.head)
        bodyParts[1] = findViewById(R.id.body)
        bodyParts[2] = findViewById(R.id.arm1)
        bodyParts[3] = findViewById(R.id.arm2)
        bodyParts[4] = findViewById(R.id.leg1)
        bodyParts[5] = findViewById(R.id.leg2)

        submitButton = this.findViewById(R.id.submit_button)
        currentPlayerScoreTextView = findViewById(R.id.current_player_score)
        currentPlayerTextView = findViewById(R.id.current_player)
        guess = findViewById(R.id.answer)

        words = resources.getStringArray(R.array.words)
        wordLayout = findViewById(R.id.word)
        currentWord = ""
        letters = findViewById(R.id.letters)
        letters.adapter = LetterAdapter(this)

        playerCount = intent.getIntExtra("playerCount",2)
        scoreArray = Array(playerCount){0}

    }

    private fun gameInit() {
        hideBodyParts()
        prepareButtons()
        generateWord()
        prepareWordLayout()

        val rnd = Random()
        currentPlayer = rnd.nextInt(playerCount)
        currentPart = 0
        numChars = currentWord.length
        numCorr = 0

        updatePlayerInfo()


        submitButton.setOnClickListener {
            val word = guess.text.toString()
            if (word == currentWord){
                scoreArray[currentPlayer]+=5
                displayEndGameDialog("Congratulations", "You win!\n\nThe answer was:\n\n$currentWord\n\nYou earned ${scoreArray[currentPlayer]} points so far!")
            }
            else{
                scoreArray[currentPlayer]-=5
                displayEndGameDialog("OOPS", "You lose!\n\nThe answer was:\n\n$currentWord\n\nYou earned ${scoreArray[currentPlayer]} points so far!")
            }

            guess.text.clear()
            it.hideKeyboard()
        }
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun updatePlayerInfo() {
        var text = "Current player: $currentPlayer"
        currentPlayerTextView.text = text

        text = "Score: ${scoreArray[currentPlayer]}"
        currentPlayerScoreTextView.text = text
    }

    private fun prepareWordLayout() {
        wordLayout.removeAllViews()
        charViews = Array(currentWord.length) { TextView(this) }

        for (i in 0 until currentWord.length) {
            charViews[i].text = currentWord[i].toString()
            charViews[i].layoutParams =
                LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT
                )
            charViews[i].gravity = Gravity.CENTER
            charViews[i].setTextColor(Color.WHITE)
            charViews[i].setBackgroundResource(R.drawable.letter_bg)

            wordLayout.addView(charViews[i])
        }
    }

    private fun hideBodyParts() {
        for (p in 0 until numParts) {
            bodyParts[p].visibility = View.INVISIBLE
        }
    }

    private fun generateWord() {
        val rand = Random()
        var newWord = words[rand.nextInt(words.size)]
        while (newWord == currentWord) newWord = words[rand.nextInt(words.size)]
        currentWord = newWord
    }

    fun letterPressed(view: View) {
        val letter = (view as TextView).text.toString()
        val letterChar = letter[0]
        view.setEnabled(false)
        view.setBackgroundResource(R.drawable.letter_down)

        var isCorrectWord = false
        for (k in 0 until currentWord.length) {
            if (currentWord[k] == letterChar) {
                isCorrectWord = true
                numCorr++
                charViews[k].setTextColor(Color.BLACK)
            }
        }

        if (isCorrectWord) {
            scoreArray[currentPlayer]++
            if (numCorr == numChars) {
                //user has won
                scoreArray[currentPlayer]+=5
                displayEndGameDialog("Congratulations", "You win!\n\nThe answer was:\n\n$currentWord\n\nYou earned ${scoreArray[currentPlayer]} points so far!")
            }
        } else if (currentPart < numParts) {
            scoreArray[currentPlayer]--
            bodyParts[currentPart].visibility = View.VISIBLE
            currentPart++
        } else {
            displayEndGameDialog("OOPS", "You lose!\n\nThe answer was:\n\n$currentWord\n\nYou earned ${scoreArray[currentPlayer]} points so far!")
        }

        changePlayer()
    }

    private fun changePlayer() {
        currentPlayer++
        if (currentPlayer==playerCount) currentPlayer=0

        updatePlayerInfo()
    }

    private fun displayEndGameDialog(title: String, message: String) {
        disableButtons()
        val build = AlertDialog.Builder(this)
        build.setTitle(title)
        build.setMessage(message)
        build.setPositiveButton(
            "Play Again"
        ) { _, _ -> this@HangmanGameActivity.gameInit() }

        build.setNegativeButton(
            "Exit"
        ) { _, _ -> this@HangmanGameActivity.finish() }

        build.show()
    }

    private fun disableButtons() {
        val numLetters = letters.childCount
        for (l in 0 until numLetters) {
            letters.getChildAt(l).isEnabled = false
        }
    }

    private fun prepareButtons() {
        val numLetters = letters.childCount
        for (i in 0 until numLetters) {
            letters.getChildAt(i).isEnabled = true
            letters.getChildAt(i).setBackgroundResource(R.drawable.letter_up)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.example.pv239_game_trio.R.menu.main_hangman, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
            R.id.action_help -> {
                showHelp()
                return true
            }
            R.id.action_show_current_high_score ->{
                showScore()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showScore() {
        val helpBuild = AlertDialog.Builder(this)

        helpBuild.setTitle("Scores")
        var string = ""
        scoreArray.forEach { string+=it.toString()+'\n' }
        helpBuild.setMessage(string)
        helpBuild.setPositiveButton("OK"){_,_->}

        helpBuild.create()
        helpBuild.show()
    }

    private fun showHelp() {
        val helpBuild = AlertDialog.Builder(this)

        helpBuild.setTitle("Help")
        helpBuild.setMessage(
            "Guess the word by selecting the letters.\n\n" +
                    "You only have 6 wrong selections then it's game over!\n\n" +
                    "+5 points for guessing correct word\n" +
                    "+1 point for guessing correct letter\n" +
                    "-5 points for guessing wrong word\n" +
                    "-1 point for guessing wrong letter\n"
        )
        helpBuild.setPositiveButton(
            "OK"
        ) { _, _ -> }

        helpBuild.create()
        helpBuild.show()
    }

}
