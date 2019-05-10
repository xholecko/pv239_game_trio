package com.example.pv239_game_trio.frontend.main.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.pv239_game_trio.R

class MainActivity : AppCompatActivity() {


    private val TAG = "GameTrioStart"

    private lateinit var mViewPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewPager = findViewById(R.id.container)

        setupViewPager(mViewPager)
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionStatePagerAdapter(supportFragmentManager)
        adapter.addFragment(MainFragment(),"MainFragment")
        adapter.addFragment(AddPlayerFragment(),"AddPlayerFragment")
        adapter.addFragment(RemovePlayerFragment(),"RemovePlayerFragment")

        viewPager.adapter = adapter
    }

    fun setViewPager(fragmentNumber: Int){
        mViewPager.currentItem = fragmentNumber
    }



}

