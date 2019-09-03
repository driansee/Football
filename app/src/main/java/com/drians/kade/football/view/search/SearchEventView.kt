package com.drians.kade.football.view.search

import com.drians.kade.football.model.Event

interface SearchEventView {
    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showEventList(data: List<Event>)
}