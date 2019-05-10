package com.example.pv239_game_trio.frontend.main.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.pv239_game_trio.R

class Start : AppCompatActivity() {


    private val TAG = "GameTrioStart"

    private lateinit var mViewPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        mViewPager = findViewById(R.id.container)
    }


    private fun setupViewPager(viewPager: ViewPager) {
        var adapter : SectionStatePagerAdapter = SectionStatePagerAdapter(supportFragmentManager)
        adapter.addFragment(StartFragment(),"StartFragment")
        adapter.addFragment(AddPlayerFragment(),"StartFragment")
        adapter.addFragment(RemovePlayerFragment(),"StartFragment")

    }



}
