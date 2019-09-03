package com.drians.kade.football.view.match

import com.drians.kade.football.model.Event
import com.drians.kade.football.model.Team

interface EventDetailView {
    fun showLoading()
    fun hideLoading()
    fun showEventDetail(data: List<Event>)
    fun showTeamDetail(dataHomeTeam: List<Team>, dataAwayTeam: List<Team>)
}