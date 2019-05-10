package com.example.pv239_game_trio.frontend.main.start

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
import com.example.pv239_game_trio.frontend.main.ChooseGameActivity

class MainFragment : Fragment() {

    private val TAG = "GameTrioMainFragment"

    private lateinit var  mainActivityButton: Button
    private lateinit var  addPlayerButton: Button
    private lateinit var  removePlayerButton: Button
    private lateinit var  restartGameButton: Button
    private lateinit var  resetPointsButton: Button

    private lateinit var  infoText: TextView
    private lateinit var  players: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.activity_main_fragment, container, false)
        val db = Room.databaseBuilder<AppDB>(context!!.applicationContext, AppDB :: class.java, "GameTrioDB").build()

        mainActivityButton = view.findViewById(R.id.button_start)
        addPlayerButton = view.findViewById(R.id.button_add)
        removePlayerButton = view.findViewById(R.id.button_remove_player)
        restartGameButton = view.findViewById(R.id.button_restart_game)
        resetPointsButton = view.findViewById(R.id.button_reset_points)

        infoText = view.findViewById(R.id.textView_info)
        players = view.findViewById(R.id.textView_players)

        mainActivityButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button START was pressed")
            openActivityMain()
        })

        //TODO
        addPlayerButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button ADD PLAYER was pressed")

        })
        //TODO
        removePlayerButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button RemovePlayer was pressed")

        })

        resetPointsButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button RESET POINTS was pressed")
            Thread{
                db.playerDAO().resetPointAllPlayers()
                Log.d(TAG,"resetPointAllPlayers() DONE")

            }.start()
        })

        restartGameButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button RESTART GAME was pressed")
            Thread{
                db.playerDAO().deleteAllPlayers()
                Log.d(TAG,"deleteAllPlayers() DONE")
            }.start()
        })

        return view
    }

    private fun openActivityMain(){
        val intent = Intent(activity, ChooseGameActivity::class.java)
        startActivity(intent)
    }


}