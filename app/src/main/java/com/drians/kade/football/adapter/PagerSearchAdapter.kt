package com.drians.kade.football.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.drians.kade.football.view.favorite.FavoriteMatchFragment
import com.drians.kade.football.view.favorite.FavoriteTeamFragment

class PagerSearchAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    private val pages = listOf(FavoriteMatchFragment(), FavoriteTeamFragment())

    override fun getItem(position: Int): Fragment {
        return pages[position] as Fragment
    }

    override fun getCount(): Int = pages.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Match"
            1 -> "Team"
            else -> throw IllegalArgumentException ("UnIdentified code")
        }
    }
}