package com.example.pv239_game_trio.backend.dao

import androidx.room.*
import com.example.pv239_game_trio.backend.entities.CharadeEntity

@Dao
interface CharadeDAO {
    /**
     * Create new charade in database.
     * @param charade to be created.
     */
    @Insert
    fun create(charade: CharadeEntity)


    /**
     * Update a charade in database.
     * @param charade to be updated.
     */
    @Update
    fun update(charade: CharadeEntity)


    /**
     * Delete charade in database.
     * @param charade to be deleted.
     */
    @Delete
    fun delete(charade: CharadeEntity)


    /**
     * @return list of all charades from database.
     */
    @Query("select * from CharadeEntity")
    fun showAllCharades() : List<CharadeEntity>

    /**
     * Delete all charades from database
     */
    @Query("delete from CharadeEntity")
    fun deleteAllCharades()


    /**
     * @return charade from DB with specific ID.
     * @param charadeId Id of charade
     */
    @Query("select * from CharadeEntity where id = :charadeId")
    fun findCharadeById(charadeId : Int) : CharadeEntity


    /**
     * @return charade from DB with specific text.
     * @param text of selected charade
     */
    @Query("select * from CharadeEntity where text = :text")
    fun findCharadeByText(text : String) : CharadeEntity
}