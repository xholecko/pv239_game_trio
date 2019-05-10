package com.example.pv239_game_trio.frontend.main.start

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class SectionStatePagerAdapter : FragmentStatePagerAdapter{

    val mFragmentList = arrayListOf<Fragment>()
    val mFragmentTitleList = arrayListOf<String>()


    constructor(fm: FragmentManager?) : super(fm)


    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }


}