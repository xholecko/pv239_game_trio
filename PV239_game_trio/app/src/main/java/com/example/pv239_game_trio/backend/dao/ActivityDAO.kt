package com.example.pv239_game_trio.backend.dao

import androidx.room.*
import com.example.pv239_game_trio.backend.entities.ActivityEntity

@Dao
interface ActivityDAO {
    /**
     * Create new activity in database.
     * @param activity to be created.
     */
    @Insert
    fun create(activity: ActivityEntity)


    /**
     * Update an activity in database.
     * @param activity to be updated.
     */
    @Update
    fun update(activity: ActivityEntity)


    /**
     * Delete activity in database.
     * @param activity to be deleted.
     */
    @Delete
    fun delete(activity: ActivityEntity)


    /**
     * @return list of all activities from database.
     */
    @Query("select * from ActivityEntity")
    fun showAllActivities() : List<ActivityEntity>

    /**
     * Delete all questions from database
     */
    @Query("delete from ActivityEntity")
    fun deleteAllActivities()


    /**
     * @return activity from DB with specific ID.
     * @param activityId Id of selected player
     */
    @Query("select * from ActivityEntity where id = :activityId")
    fun findActivityById(activityId : Int) : ActivityEntity


    /**
     * @return activity from DB with specific text.
     * @param text of selected activity
     */
    @Query("select * from ActivityEntity where text = :text")
    fun findActivityByText(text : String) : ActivityEntity
}