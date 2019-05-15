package com.example.pv239_game_trio.frontend.main.start

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.pv239_game_trio.R
import com.example.pv239_game_trio.backend.AppDB
import com.google.android.material.textfield.TextInputLayout

class AddTeamFragment : Fragment() {



    private val TAG = "GameTrioAddTeamFrag"

    private lateinit var  addTeamButton: Button
    private lateinit var  removeTeamsButton: Button


    private lateinit var  teams: TextView

    lateinit var mRecyclerView: RecyclerView

    lateinit var textInputWord: TextInputLayout



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view : View = inflater.inflate(R.layout.activity_main_add_player_fragment, container, false)
        val db = Room.databaseBuilder<AppDB>(context!!.applicationContext, AppDB :: class.java, "GameTrioDB").build()
        Log.d(TAG,"onCreateView() ")

        textInputWord = view.findViewById(R.id.input)
        teams = view.findViewById(R.id.textView_players)

        addTeamButton = view.findViewById(R.id.button_add_player)
        removeTeamsButton = view.findViewById(R.id.button_remove_all)

        mRecyclerView = view.findViewById(R.id.recycler_view)



        //TODO
//        addPlayerButton.setOnClickListener(View.OnClickListener {
//            Log.d(TAG,"button AddPlayer was pressed")
//            if (validateInput(db)){
//
//                var dbValidation = "ok"
//
//                var name = textInputWord.editText!!.text.toString().trim()
//                Thread{
//
//
//                    if (db.playerDAO().findPlayerByName(name) != null){
//                        Log.d(TAG,"There is player with the same name already")
//                        dbValidation = "nameProblem"
//                    }
//
//                    if (db.playerDAO().showAllPlayers().size >= 6){
//                        Log.d(TAG,"Max number of players is 6")
//                        dbValidation = "sizeProblem"
//                    }
//
//                    if (dbValidation == "ok"){
//                        var player = TeamEntity()
//                        player.id = db.playerDAO().showAllPlayers().size
//                        player.name = name
//                        player.points = 0
//                        db.playerDAO().create(player)
//                        Log.d(TAG,"Player was created")
//                        val a = db.playerDAO().findPlayerById(player.id)
//                        Log.d(TAG,a.toString())
//                    }
//
//                }.start()
//                    if (dbValidation == "sizeProblem"){
//                        textInputWord.error = "Max number of players is 6"
//                    }
//
//                    if (dbValidation == "nameProblem") {
//                        textInputWord.error = "There is already player with the same name"
//
//                    }
//
//
//            } else{
//                Toast.makeText(context, "Field can not be empty", Toast.LENGTH_SHORT).show()
//            }
//
//        })
//
//
//        removePlayersButton.setOnClickListener(View.OnClickListener {
//            Log.d(TAG,"button REMOVE PLAYER was pressed")
//            Thread{
//                var a = db.playerDAO().showAllPlayers().toString()
//                Log.d(TAG,a)
//                db.playerDAO().deleteAllPlayers()
//                Log.d(TAG,"deleteAllPlayers() DONE")
//            }.start()
//        })

        return view
    }




    private fun validateInput(db : AppDB): Boolean {
        Log.d(TAG,"Validation started")
        val name = textInputWord.editText!!.text.toString().trim()

        if (name.isEmpty()){
            textInputWord.error = "Field can not be empty"
            Log.d(TAG,"Validation not succesful")
            return false
        }
        if (name.length > 20){
            textInputWord.error = "Name can not have more than 20 characters"
            Log.d(TAG,"Validation not succesful")
            return false
        }
        else {
            textInputWord.error = null
            Log.d(TAG,"Validation succesful")
            return true
        }

    }

}