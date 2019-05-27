package com.example.pv239_game_trio.backend.dao

import android.util.Log
import androidx.room.*
import com.example.pv239_game_trio.backend.entities.PlayerEntity

@Dao
interface PlayerDAO {

    /**
     * Adds new Player in to database.
     * @param player to be created.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(player: PlayerEntity)


    /**
     * Update a Player in database.
     * @param player to be updated.
     */
    @Update
    fun update(player: PlayerEntity)


    /**
     * Delete Player from database.
     * @param player to be deleted.
     */
    @Delete
    fun delete(player: PlayerEntity)


    /**
     * @return list of all Players from database.
     */
    @Query("select * from PlayerEntity")
    fun showAllPlayers() : Array<PlayerEntity>





    /**
     * Delete all Players from database
     */
    @Query("delete from PlayerEntity")
    fun deleteAllPlayers()


    /**
     * @return Player from DB with specific ID.
     * @param playerId Id of selected Player
     */
    @Query("select * from PlayerEntity where id = :playerId")
    fun findPlayerById(playerId : Int) : PlayerEntity


    /**
     * @return Player from DB with specific name.
     * @param name of selected Player
     */
    @Query("select * from PlayerEntity where name = :name")
    fun findPlayerByName(name : String) : PlayerEntity


    /**
     * @return number of points selected Player has
     * @param playerId id of selected Player
     */
    @Query("select points from PlayerEntity where id = :playerId")
    fun getPointsById(playerId: Int) : Int


    /**
     * Adds / Remove selected number of points from selected Player number of points
     * @param playerId id of selected Player
     */
    @Query("update PlayerEntity set points = points + :newPoints where id = :playerId")
    fun addPointsById(playerId: Int, newPoints : Int)


    /**
     * Sets all Players to have 0 points
     */
    @Query("update PlayerEntity set points = 0 where points != 0")
    fun resetPointAllPlayers()

    /**
     * Adds selected number of points to all Players but one
     * @param playerId id of selected Player who will not get the points
     */
    @Query("update PlayerEntity set points = points + :newPoints where id != :playerId")
    fun addPointsToAllButOneById(playerId: Int, newPoints : Int)


    /**
     * Adds selected number of points to two players
     * @param playerId1 id of first Player who will get the points
     * @param playerId2 id of second Player who will get the points
     */
    @Query("update PlayerEntity set points = points + :newPoints where id == :playerId1 and id == :playerId2")
    fun addPointsToTwoPlayers(playerId1: Int,playerId2: Int, newPoints : Int)

}