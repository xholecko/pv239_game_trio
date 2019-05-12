package com.example.pv239_game_trio.backend.dao

import androidx.room.*
import com.example.pv239_game_trio.backend.entities.TikBumEntity


@Dao
interface TikBumDAO {
    /**
     * Create new word in database.
     * @param word to be created.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(word: TikBumEntity)


    /**
     * Update a word in database.
     * @param word to be updated.
     */
    @Update
    fun update(word: TikBumEntity)


    /**
     * Delete word from database.
     * @param word to be deleted.
     */
    @Delete
    fun delete(word: TikBumEntity)


    /**
     * @return list of all words from database.
     */
    @Query("select * from TikBumEntity")
    fun showAllWords() : List<TikBumEntity>


    /**
     * Delete all words from database
     */
    @Query("delete from TikBumEntity")
    fun deleteAllWords()

    /**
     * @return word from DB with specific ID.
     * @param wordId Id of selected word
     */
    @Query("select * from TikBumEntity where id = :wordId")
    fun findWordById(wordId : Int) : TikBumEntity
}