package com.drians.kade.football.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.drians.kade.football.view.match.EventNextFragment
import com.drians.kade.football.view.match.EventPreviousFragment
import com.drians.kade.football.view.standings.TableFragment
import com.drians.kade.football.view.team.TeamFragment

class PagerLeagueAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    private val pages = listOf(
        EventPreviousFragment(),
        EventNextFragment(),
        TableFragment(),
        TeamFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position] as Fragment
    }

    override fun getCount(): Int = pages.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Last Match"
            1 -> "Next Match"
            2 -> "Standings"
            3 -> "Team"
            else -> throw IllegalArgumentException ("UnIdentified code")
        }
    }
}