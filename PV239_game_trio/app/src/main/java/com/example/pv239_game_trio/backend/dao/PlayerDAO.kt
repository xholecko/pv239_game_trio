package com.example.pv239_game_trio.backend.dao

import android.util.Log
import androidx.annotation.Nullable
import androidx.room.*
import com.example.pv239_game_trio.backend.entities.PlayerEntity

@Dao
interface PlayerDAO {
    /**
     * Create new player in database.
     * @param player to be created.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(player: PlayerEntity)


    /**
     * Update a player in database.
     * @param player to be updated.
     */
    @Update
    fun update(player: PlayerEntity)


    /**
     * Delete player in database.
     * @param player to be deleted.
     */
    @Delete
    fun delete(player: PlayerEntity)


    /**
     * @return list of all players from database.
     */
    @Query("select * from PlayerEntity")
    fun showAllPlayers() : List<PlayerEntity>


    /**
     * Delete all players from database
     */
    @Query("delete from PlayerEntity")
    fun deleteAllPlayers()


    /**
     * @return player from DB with specific ID.
     * @param playerId Id of selected player
     */
    @Query("select * from PlayerEntity where id = :playerId")
    fun findPlayerById(playerId : Int) : PlayerEntity


    /**
     * @return player from DB with specific name.
     * @param name of selected player
     */
    @Query("select * from PlayerEntity where name = :name")
    fun findPlayerByName(name : String) : PlayerEntity


    /**
     * @return number of points selected player has
     * @param playerId id of selected player
     */
    @Query("select points from PlayerEntity where id = :playerId")
    fun getPointsById(playerId: Int) : Int


    /**
     * Adds / Remove selected number of points from selected player number of points
     * @param playerId id of selected player
     */
    @Query("update PlayerEntity set points = points + :newPoints where id = :playerId")
    fun addPointsById(playerId: Int, newPoints : Int)

    /**
     * Adds / Remove selected number of points from selected player number of points
     * @param playerId id of selected player
     */
    @Query("update PlayerEntity set points = points + :newPoints where team = :team")
    fun addPointsByTeam(team: Int, newPoints : Int)


    /**
     * Sets all players to have 0 points
     */
    @Query("update PlayerEntity set points = 0 where points != 0")
    fun resetPointAllPlayers()

    /**
     * Adds selected number of points to all players but one
     * @param playerId id of selected player who will not get the points
     */
    @Query("update PlayerEntity set points = points + :newPoints where id != :playerId")
    fun addPointsToAllButOneById(playerId: Int, newPoints : Int)

}