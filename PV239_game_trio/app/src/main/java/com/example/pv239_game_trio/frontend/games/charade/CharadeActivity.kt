package com.example.pv239_game_trio.frontend.games.charade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB
import com.example.pv239_game_trio.backend.entities.ActivityEntity
import java.util.Random
import androidx.room.Room
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView

class CharadeActivity : AppCompatActivity() {

    private val TAG = "GameTrioCharade"

    private lateinit var db : AppDB
    private lateinit var random : Random

    private lateinit var timer: CountDownTimer
    private var timerRunning : Boolean = false

    private lateinit var buttonStart : Button
    private lateinit var activityText: TextView
    private lateinit var activityType: TextView
    private lateinit var countdown: TextView

    private lateinit var allActivities: List<ActivityEntity>

    enum class ActivityType {
        DESCRIPTION, DRAWING, PANTOMIME
    }

    private val WORDS = setOf("Elephant", "Mole's hill", "Rebellion", "Guitar", "Chimpanzee",
        "The Sword in the Stone", "The Magic Carpet", "Car", "Human", "Apple", "Vegetables",
        "Helicopter", "Card", "Japan", "France", "Brazil", "Germany", "Hercules", "Window",
        "Computer", "Calculator", "Exam", "Knights of the Round Table", "Banana", "Tomato",
        "Hero", "Villain", "Movie", "Deer", "Firefighter", "Giraffe", "Archer", "Nightmare",
        "House", "Mountain", "Cloud", "Water", "Town", "City", "Nightmare", "Airplane",
        "Venture", "Murmur", "Password", "Procrastinate", "Teach", "Sleep", "Write", "Show",
        "Present", "Give", "Relax", "Shave", "Keyboard", "Yell", "Paddle", "Bunny", "Photograph",
        "Crawl", "Scold", "Skull", "The One Ring", "Dragon", "Wizard", "Warrior", "Cleric",
        "Bear's lair", "Eagle's nest", "The Chosen One", "Spaceship", "Village", "Hope",
        "Fellowship", "Betrayal", "Sacrifice", "Emperor", "Elf", "Dwarf", "Ninja", "Tiger",
        "Lion", "Dinosaur", "Shark", "Okapi", "Panda", "Policeman", "Tailor", "Salesman",
        "Driver", "Bus", "Wolf", "Video game", "Servant", "Magic", "Legend", "Flamingo", "Jewel",
        "Question", "Activity", "Charade", "Pelican", "Space", "Brain", "Tusk", "Hand", "Leg",
        "Mouth", "Eye", "Ear", "Horse", "Bone", "Brave", "Big", "Small", "Hilarious", "Funny",
        "Sexy", "Smart", "Tall", "Short", "Hot", "Cold", "Warm", "Ladder", "Chaos", "Taxi",
        "Fan", "Wipe", "Flute", "Chair", "Table", "Punch", "Disorganized", "Conversation",
        "Accept", "Planet", "Start", "Galaxy", "Vandalize", "Shore", "Machine", "Old", "Young",
        "Walking a dog", "Surfing a wave", "Listening a music", "Playing a video game",
        "Painting a picture", "Delivering mail", "Mowing a lawn", "Climbing a mountain",
        "Watching movie", "Sorting mail", "Ordering food", "Washing a car", "Visiting the zoo",
        "Playing the basketball", "Operating on a patient", "Arranging flowers", "King", "Mace")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charade)
        Log.d(TAG,"CharadeActivity created")

        db = Room.databaseBuilder(applicationContext, AppDB :: class.java, "GameTrioDB").build()
        random = Random()

        Thread {
            insertActivitiesIntoDatabase()
            allActivities = db.activityDAO().showAllActivities()
        }.start()

        buttonStart = findViewById(R.id.button_start_charade)
        activityText = findViewById(R.id.text_activity_word)
        activityType = findViewById(R.id.text_activity_type)
        countdown = findViewById(R.id.activity_countdown)

        updateToNewActivity()

        buttonStart.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"Button for starting charade was pressed")
            setTimer()
        })
    }

    /**
     * Updates text views with new information
     */
    private fun updateToNewActivity() {
        var activity : Pair<ActivityEntity, ActivityType> = generateActivity(allActivities)
        activityText.text = activity.first.text
        activityType.text = activity.second.toString()
        countdown.text = "60"
        Log.d(TAG,"Text and type of activity were updated")
    }

    /**
     * Creates activities from given collection of words and saves them into database
     */
    private fun insertActivitiesIntoDatabase() {
        var insertedWords = mutableSetOf<String>()
        for (word in WORDS) {
            if(insertedWords.contains(word)) {
                continue
            }

            var activity = ActivityEntity()
            activity.text = word

            Thread {
                db.activityDAO().create(activity)
            }.start()

            insertedWords.add(word)
        }
    }

    /**
     * Generates activity with random activity type
     */
    private fun generateActivity(availableActivities : List<ActivityEntity>): Pair<ActivityEntity, ActivityType> {
        var activity = availableActivities.get(random.nextInt(availableActivities.size))
        var activityType = ActivityType.values()[random.nextInt(ActivityType.values().size)]

        return Pair(activity, activityType)
    }

    private fun setTimer(){
        timer = object: CountDownTimer(60000,1000) {

            override fun onTick(millisUntilFinished: Long) {
                buttonStart.visibility = View.INVISIBLE
                countdown.text = (millisUntilFinished / 1000).toString()
                timerRunning = true
            }

            override fun onFinish() {
                buttonStart.visibility = View.VISIBLE
                timerRunning = false
            }
        }
        timer.start()
    }

    private fun addPoints(charadeActorId : Int, charadeResponderId : Int) {
        Thread {
            db.playerDAO().addPointsById(charadeActorId, 1)
            db.playerDAO().addPointsById(charadeResponderId, 1)
        }.start()
    }
}
