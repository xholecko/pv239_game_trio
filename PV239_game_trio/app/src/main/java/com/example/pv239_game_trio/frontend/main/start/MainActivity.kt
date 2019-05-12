package com.example.pv239_game_trio.frontend.main.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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
        adapter.addFragment(AddPlayerFragment(),"AddPlayerFragment")
        adapter.addFragment(RemovePlayerFragment(),"RemovePlayerFragment")

        viewPager.adapter = adapter
    }

    fun setViewPager(fragmentNumber: Int){
        Log.d(TAG,"setViewPager() " + fragmentNumber)
        mViewPager.currentItem = fragmentNumber
    }



}

