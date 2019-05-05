package com.example.pv239_game_trio.backend.dao

import androidx.room.*
import com.example.pv239_game_trio.backend.entities.PlayerEntity

@Dao
interface PlayerDAO {
    /**
     * Create new player in database.
     * @param player to be created.
     */
    @Insert
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

    @Query("select * from PlayerEntity")
    fun showAllPlayers() : List<PlayerEntity>

    @Query("delete from PlayerEntity where id < 10")
    fun deleteAllPlayers()
}