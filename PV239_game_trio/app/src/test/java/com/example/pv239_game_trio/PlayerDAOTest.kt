package com.example.pv239_game_trio

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.pv239_game_trio.backend.AppDB
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PlayerDAOTest {

    //private lateinit var playerDAO: PlayerDAO
    private lateinit var db: AppDB

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(), AppDB::class.java)
            .build()
        //playerDAO = db.playerDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun readAndWriteTests() {
//        val player = PlayerEntity(name = "Peter", points = 2,team = 1)
//        val playerId = db.playerDAO().create(player)
//        val playerFromDb = db.playerDAO().getPlayer()
//        assertEquals(playerId, 1)
//        assertEquals(playerFromDb?.name, player.name)


    }
}