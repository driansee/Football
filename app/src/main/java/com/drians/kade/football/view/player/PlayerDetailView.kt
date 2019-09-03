package com.drians.kade.football.view.player

import com.drians.kade.football.model.Player

interface PlayerDetailView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerDetail(data: List<Player>)
}