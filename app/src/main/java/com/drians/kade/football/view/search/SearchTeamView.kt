package com.drians.kade.football.view.search

import com.drians.kade.football.model.Team

interface SearchTeamView {
    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showTeamtList(data: List<Team>)
}