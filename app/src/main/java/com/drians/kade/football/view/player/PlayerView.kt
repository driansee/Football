package com.drians.kade.football.view.player

import com.drians.kade.football.model.Player

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: List<Player>)
}