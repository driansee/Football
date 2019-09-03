package com.drians.kade.football.view.match

import com.drians.kade.football.model.Event

interface EventView {
        fun showLoading()
        fun hideLoading()
        fun showEmptyData()
        fun showEventList(data: List<Event>)
}