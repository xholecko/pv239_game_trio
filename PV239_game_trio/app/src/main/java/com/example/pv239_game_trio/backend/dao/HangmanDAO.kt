package com.example.pv239_game_trio.backend.dao

import androidx.room.*
import com.example.pv239_game_trio.backend.entities.HangmanEntity
import com.example.pv239_game_trio.backend.entities.TikBumEntity


@Dao
interface HangmanDAO {
    /**
     * Create new word in database.
     * @param word to be created.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(word: HangmanEntity)

    /**
     * Delete word from database.
     * @param word to be deleted.
     */
    @Delete
    fun delete(word: HangmanEntity)


    /**
     * @return list of all words from database.
     */
    @Query("select * from HangmanEntity")
    fun showAllWords() : List<HangmanEntity>


    /**
     * Delete all words from database
     */
    @Query("delete from HangmanEntity")
    fun deleteAllWords()

    /**
     * @return word from DB with specific ID.
     * @param wordId Id of selected word
     */
    @Query("select * from HangmanEntity where id = :wordId")
    fun findWordById(wordId : Int) : HangmanEntity
}