package com.drians.kade.football.view.team

import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.api.TheSportDBApi
import com.drians.kade.football.model.TeamResponse
import com.drians.kade.football.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamDetailPresenter(private val view: TeamDetailView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()

        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                .doRequestAsync(TheSportDBApi.getTeamDetail(teamId)).await(),
                TeamResponse::class.java)

            view.showTeamDetail(data.teams)
            view.hideLoading()
        }
    }
}