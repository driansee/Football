package com.drians.kade.football.view.match

import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.api.TheSportDBApi
import com.drians.kade.football.model.EventResponse
import com.drians.kade.football.model.TeamResponse
import com.drians.kade.football.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class EventDetailPresenter(private val view: EventDetailView,
                           private val apiRepository: ApiRepository,
                           private val  gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getEventDetail(idEvent: String){
        view.showLoading()
        GlobalScope.launch(context.main){
            val  data = gson.fromJson(apiRepository.doRequestAsync(
                TheSportDBApi.getEventDetail(idEvent)).await(), EventResponse::class.java)
                view.hideLoading()
                data.events?.let { view.showEventDetail(it) }
        }
    }

    fun getTeamDetail(idHomeTeam: String?, idAwayTeam: String?) {
        view.showLoading()
        GlobalScope.launch(context.main){
            val dataHomeTeam = gson.fromJson(apiRepository
                    .doRequestAsync(TheSportDBApi.getTeamDetail(idHomeTeam)).await(),
                    TeamResponse::class.java)

            val dataAwayTeam = gson.fromJson(apiRepository
                    .doRequestAsync(TheSportDBApi.getTeamDetail(idAwayTeam)).await(),
                    TeamResponse::class.java)
                view.hideLoading()
                view.showTeamDetail(dataHomeTeam.teams, dataAwayTeam.teams)

        }
    }
}