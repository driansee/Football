package com.drians.kade.football.view.team

import com.drians.kade.football.model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}