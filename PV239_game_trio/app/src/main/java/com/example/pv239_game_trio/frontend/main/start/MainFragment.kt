package com.example.pv239_game_trio.frontend.main.start

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB
import com.example.pv239_game_trio.backend.dto.TeamDTO
import com.example.pv239_game_trio.backend.entities.TeamEntity
import com.example.pv239_game_trio.frontend.main.ChooseGameActivity


class MainFragment : Fragment() {

    private val TAG = "GameTrioMainFragment"

    private lateinit var  startActivityButton: Button
    private lateinit var  addPlayerButton: Button
    private lateinit var  removeTeamButton: Button
    private lateinit var  restartGameButton: Button
    private lateinit var  resetPointsButton: Button

    private lateinit var  infoText: TextView
    private lateinit var  teams: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.activity_main_fragment, container, false)
        val db = Room.databaseBuilder<AppDB>(context!!.applicationContext, AppDB :: class.java, "GameTrioDB").build()
        createPlayers(db)

        startActivityButton = view.findViewById(R.id.button_start)
        addPlayerButton = view.findViewById(R.id.button_add)
        removeTeamButton = view.findViewById(R.id.button_remove_player)
        restartGameButton = view.findViewById(R.id.button_restart_game)
        resetPointsButton = view.findViewById(R.id.button_reset_points)



        infoText = view.findViewById(R.id.textView_info)
        teams = view.findViewById(R.id.textView_players)



        teams.setText(showPlayers(db))

        startActivityButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button START was pressed")
            openActivityMain()
        })

        //TODO
        addPlayerButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button ADD TEAM was pressed")
            val fragment = AddTeamFragment()
            replaceFragment(fragment)
            //val activity = MainActivity()

            MainActivity().setViewPager(0)
        })

        //TODO
        removeTeamButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button RemoveTeams was pressed")

        })




        resetPointsButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button RESET POINTS was pressed")

            val builder : AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to reset all points?")
            builder.setPositiveButton("Yes"){dialog, which ->
                    Thread{
                        db.playerDAO().resetPointAllTeams()
                        Log.d(TAG,"resetPointAllTeams() DONE")
                    }.start()
                }
            builder.setNegativeButton("No"){dialog, which ->
                Log.d(TAG,"resetPointAllTeams() canceled by user")

            }
            builder.show()



        })

        restartGameButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button RESTART GAME was pressed")



            val builder : AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to restart game?")
            builder.setPositiveButton("Yes"){dialog, which ->
                Thread{
                    db.playerDAO().deleteAllTeams()
                    Log.d(TAG,"deleteAllTeams() DONE")
                }.start()
            }
            builder.setNegativeButton("No"){dialog, which ->
                Log.d(TAG,"deleteAllTeams() canceled by user")

            }
            builder.show()
        })

        return view
    }

    private fun openActivityMain(){
        val intent = Intent(activity, ChooseGameActivity::class.java)
        startActivity(intent)
    }

    private fun replaceFragment(someFragment: Fragment) {
        val transaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.container, someFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    private fun showPlayers(db: AppDB) : String{
        var actualPlayers = ""

        Thread{
            var playersInDB = db.playerDAO().showAllTeams()

            for (playerDb in playersInDB){
                var playerDTO = TeamDTO()
                playerDTO.name = playerDb.name
                playerDTO.points = playerDb.points
                actualPlayers = actualPlayers + playerDTO.toString() + "/n"
            }
        }.start()

        return actualPlayers
    }


    private fun createPlayers(db: AppDB){
        Thread {
            var player1 = TeamEntity()
            player1.id = 0
            player1.name = "Peter"
            player1.points = 0


            var player2 = TeamEntity()
            player2.id = 1
            player2.name = "Marcel"
            player2.points = 0


            var player3 = TeamEntity()
            player3.id = 2
            player3.name = "Jitka"
            player3.points = 0


            var player4 = TeamEntity()
            player4.id = 3
            player4.name = "Sara"
            player4.points = 0



            db.playerDAO().create(player1)
            db.playerDAO().create(player2)
            db.playerDAO().create(player3)
            db.playerDAO().create(player4)

            Log.d(TAG,"4 teams created")

        }.start()
    }



}