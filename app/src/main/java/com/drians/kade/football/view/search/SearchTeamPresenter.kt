package com.drians.kade.football.view.search

import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.api.TheSportDBApi
import com.drians.kade.football.model.TeamSearchResponse
import com.drians.kade.football.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchTeamPresenter(private val view: SearchTeamView,
                          private val apiRepository: ApiRepository,
                          private val  gson: Gson,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamSearch(teamName: String){
        view.showLoading()

        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository.doRequestAsync(
                TheSportDBApi.getTeamSearch(teamName)).await(), TeamSearchResponse::class.java)

            view.hideLoading()
            try {
                data.teams?.let { view.showTeamtList(it) }
            } catch (e : NullPointerException) {
                view.showEmptyData()
            }
        }
    }
}