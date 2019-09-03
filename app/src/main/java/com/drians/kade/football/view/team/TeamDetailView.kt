package com.drians.kade.football.view.team

import com.drians.kade.football.model.Team

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<Team>)
}