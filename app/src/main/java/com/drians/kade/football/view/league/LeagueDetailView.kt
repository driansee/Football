package com.drians.kade.football.view.league

import com.drians.kade.football.model.League

interface LeagueDetailView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueDetail(data: List<League>)
}