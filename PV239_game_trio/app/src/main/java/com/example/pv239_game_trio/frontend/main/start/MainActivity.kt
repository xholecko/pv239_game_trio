package com.example.pv239_game_trio.frontend.main.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager.widget.ViewPager
import com.example.pv239_game_trio.R

class MainActivity : AppCompatActivity() {


    private val TAG = "GameTrioMainActivity"

    private lateinit var mViewPager : ViewPager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"onCreate()")


        mViewPager = findViewById(R.id.container)

        setupViewPager(mViewPager)
    }


    private fun setupViewPager(viewPager: ViewPager) {
        Log.d(TAG,"setupViewPager() " + viewPager.toString())

        val adapter = SectionStatePagerAdapter(supportFragmentManager)
        adapter.addFragment(MainFragment(),"MainFragment")
        adapter.addFragment(AddTeamFragment(),"AddTeamFragment")
        adapter.addFragment(RemoveTeamFragment(),"RemoveTeamFragment")

        viewPager.adapter = adapter
    }

    fun setViewPager(fragmentNumber: Int){
        Log.d(TAG,"setViewPager() " + fragmentNumber)
        mViewPager.currentItem = fragmentNumber
    }



}

