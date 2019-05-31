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
     * Delete word from database.
     * @param word to be deleted.
     */
    @Delete
    fun delete(word: TikBumEntity)


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


    /**
     * @return list of all Syllables from database.
     */
    @Query("select * from TikBumEntity")
    fun showAllSyllables() : Array<TikBumEntity>
}