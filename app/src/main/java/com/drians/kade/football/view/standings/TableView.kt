package com.drians.kade.football.view.standings

import com.drians.kade.football.model.Table

interface TableView {
    fun showLoading()
    fun hideLoading()
    fun showTableList(data: List<Table>)
}