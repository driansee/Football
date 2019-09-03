package com.drians.kade.football.view.league

import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.api.TheSportDBApi
import com.drians.kade.football.model.LeagueResponse
import com.drians.kade.football.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueDetailPresenter(private val view: LeagueDetailView,
                            private val apiRepository: ApiRepository,
                            private val  gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getLeagueDetail(leagueId: String){
        view.showLoading()
        GlobalScope.launch(context.main){
            val  data = gson.fromJson(apiRepository.doRequestAsync(
                TheSportDBApi.getLeagueDetail(leagueId)).await(), LeagueResponse::class.java)
            view.hideLoading()
            view.showLeagueDetail(data.leagues)
        }
    }
}