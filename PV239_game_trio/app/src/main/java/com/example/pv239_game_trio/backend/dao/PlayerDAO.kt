package com.example.pv239_game_trio.backend.dao

import androidx.room.*
import com.example.pv239_game_trio.backend.entities.PlayerEntity

@Dao
interface PlayerDAO {
    /**
     * Create new Player in database.
     * @param Player to be created.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(Player: PlayerEntity)


    /**
     * Update a Player in database.
     * @param Player to be updated.
     */
    @Update
    fun update(Player: PlayerEntity)


    /**
     * Delete Player in database.
     * @param Player to be deleted.
     */
    @Delete
    fun delete(Player: PlayerEntity)


    /**
     * @return list of all Players from database.
     */
    @Query("select * from PlayerEntity")
    fun showAllPlayers() : List<PlayerEntity>


    /**
     * Delete all Players from database
     */
    @Query("delete from PlayerEntity")
    fun deleteAllPlayers()


    /**
     * @return Player from DB with specific ID.
     * @param PlayerId Id of selected Player
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
     * @param PlayerId id of selected Player
     */
    @Query("select points from PlayerEntity where id = :playerId")
    fun getPointsById(playerId: Int) : Int


    /**
     * Adds / Remove selected number of points from selected Player number of points
     * @param PlayerId id of selected Player
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
     * @param PlayerId id of selected Player who will not get the points
     */
    @Query("update PlayerEntity set points = points + :newPoints where id != :playerId")
    fun addPointsToAllButOneById(playerId: Int, newPoints : Int)


    /**
     * Adds selected number of points to two players
     * @param PlayerId1 id of first Player who will get the points
     * @param PlayerId2 id of second Player who will get the points
     */
    @Query("update PlayerEntity set points = points + :newPoints where id == :playerId1 and id == :playerId2")
    fun addPointsToTwoPlayers(playerId1: Int,playerId2: Int, newPoints : Int)

}