package com.example.pv239_game_trio.backend.dao

import androidx.room.*
import com.example.pv239_game_trio.backend.entities.TeamEntity

@Dao
interface TeamDAO {
    /**
     * Create new team in database.
     * @param team to be created.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(team: TeamEntity)


    /**
     * Update a team in database.
     * @param team to be updated.
     */
    @Update
    fun update(team: TeamEntity)


    /**
     * Delete team in database.
     * @param team to be deleted.
     */
    @Delete
    fun delete(team: TeamEntity)


    /**
     * @return list of all teams from database.
     */
    @Query("select * from TeamEntity")
    fun showAllTeams() : List<TeamEntity>


    /**
     * Delete all teams from database
     */
    @Query("delete from TeamEntity")
    fun deleteAllTeams()


    /**
     * @return team from DB with specific ID.
     * @param teamId Id of selected team
     */
    @Query("select * from TeamEntity where id = :teamId")
    fun findTeamById(teamId : Int) : TeamEntity


    /**
     * @return team from DB with specific name.
     * @param name of selected team
     */
    @Query("select * from TeamEntity where name = :name")
    fun findTeamByName(name : String) : TeamEntity


    /**
     * @return number of points selected team has
     * @param teamId id of selected team
     */
    @Query("select points from TeamEntity where id = :teamId")
    fun getPointsById(teamId: Int) : Int


    /**
     * Adds / Remove selected number of points from selected team number of points
     * @param teamId id of selected team
     */
    @Query("update TeamEntity set points = points + :newPoints where id = :teamId")
    fun addPointsById(teamId: Int, newPoints : Int)


    /**
     * Sets all teams to have 0 points
     */
    @Query("update TeamEntity set points = 0 where points != 0")
    fun resetPointAllTeams()

    /**
     * Adds selected number of points to all teams but one
     * @param teamId id of selected team who will not get the points
     */
    @Query("update TeamEntity set points = points + :newPoints where id != :teamId")
    fun addPointsToAllButOneById(teamId: Int, newPoints : Int)

}