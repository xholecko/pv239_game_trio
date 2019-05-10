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
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.frontend.main.MainActivity

class StartFragment : Fragment() {

    private val TAG = "GameTrioStartFragment"

    private lateinit var  mainActivityButton: Button
    private lateinit var  addPlayerButton: Button
    private lateinit var  removePlayerButton: Button
    private lateinit var  restartGameButton: Button
    private lateinit var  infoText: TextView
    private lateinit var  players: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.activity_start_fragment, container, false)

        mainActivityButton = view.findViewById(R.id.button_start)
        addPlayerButton = view.findViewById(R.id.button_add)
        removePlayerButton = view.findViewById(R.id.button_start)
        restartGameButton = view.findViewById(R.id.button_add)
        infoText = view.findViewById(R.id.textView_info)
        players = view.findViewById(R.id.textView_players)

        mainActivityButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"button START was pressed")
            openActivityMain()
        })

        return view
    }

    private fun openActivityMain(){

        //TODO activity? this?
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
    }
}