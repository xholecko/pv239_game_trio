package com.example.pv239_game_trio.backend

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.pv239_game_trio.backend.entities.CharadeEntity
import com.example.pv239_game_trio.backend.entities.PlayerEntity
import com.example.pv239_game_trio.backend.entities.TikBumEntity

class App : Application() {
    private val TAG = "GameTrioApp"

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


    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"onCreate()")
        val db = Room.databaseBuilder<AppDB>(applicationContext, AppDB :: class.java, "GameTrioDB").build()

        //createPlayers(db)
        createWords(db)
        createCharades(db)
    }




    private fun createPlayers(db: AppDB){
        Thread {
            var player1 = PlayerEntity()
            player1.id = 1
            player1.name = "ADMIN"
            player1.points = 0

            db.playerDAO().create(player1)

            Log.d(TAG,"1 player created")

        }.start()
    }

    private fun createWords(db: AppDB){
        Thread {
            var word0 = TikBumEntity()
            word0.id = 0
            word0.word = "lol"

            var word1 = TikBumEntity()
            word1.id = 1
            word1.word = "ha"

            var word2 = TikBumEntity()
            word2.id = 2
            word2.word = "na"

            var word3 = TikBumEntity()
            word3.id = 3
            word3.word = "ba"

            var word4 = TikBumEntity()
            word4.id = 4
            word4.word = "fa"

            var word5 = TikBumEntity()
            word5.id = 5
            word5.word = "ra"

            var word6 = TikBumEntity()
            word6.id = 6
            word6.word = "la"

            db.tikBumDAO().create(word0)
            db.tikBumDAO().create(word1)
            db.tikBumDAO().create(word2)
            db.tikBumDAO().create(word3)
            db.tikBumDAO().create(word4)
            db.tikBumDAO().create(word5)
            db.tikBumDAO().create(word6)
            Log.d(TAG,"7 words created")

        }.start()
    }

    private fun createCharades(db : AppDB) {
        Thread {
            var insertedWords = mutableSetOf<String>()
            for (word in WORDS) {
                if (insertedWords.contains(word)) {
                    continue
                }

                var charade = CharadeEntity()
                charade.text = word

                db.charadeDAO().create(charade)
                insertedWords.add(word)
            }
        }.start()

        /*
        Thread {
            var charade = CharadeEntity()
            charade.text = "IT WORKS!!(MUHAHAHA)"
            db.charadeDAO().create(charade)
        }.start()
        */
    }
}
