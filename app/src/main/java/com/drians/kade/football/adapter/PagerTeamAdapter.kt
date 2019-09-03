package com.drians.kade.football.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.drians.kade.football.view.player.PlayerFragment
import com.drians.kade.football.view.team.TeamInfoFragment


class PagerTeamAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    private val pages = listOf(TeamInfoFragment(), PlayerFragment())

    override fun getItem(position: Int): Fragment {
        return pages[position] as Fragment
    }

    override fun getCount(): Int = pages.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Team Info"
            1 -> "Player"
            else -> throw IllegalArgumentException ("UnIdentified code")
        }
    }
}