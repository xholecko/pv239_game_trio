package com.example.pv239_game_trio.frontend.games.hangman

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.pv239_game_trio.R
import android.widget.Button


class LetterAdapter() : android.widget.BaseAdapter() {
    private var letters: Array<String> = Array(26){ String() }
    private lateinit var letterInf: LayoutInflater

    constructor(c: Context) : this() {
        for (a in 0 until letters.size) {
            letters[a] = "" + (a + 'A'.toInt()).toChar()
        }
        letterInf = LayoutInflater.from(c)
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val letterBtn: Button = if (convertView == null) {
            letterInf.inflate(R.layout.letter, parent, false) as Button
        } else {
            convertView as Button
        }
        letterBtn.text = letters[position]
        return letterBtn
    }

    override fun getItem(position: Int): Any? {
       return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return letters.size
    }

}