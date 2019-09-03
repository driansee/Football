package com.drians.kade.football.view.match

import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.api.TheSportDBApi
import com.drians.kade.football.model.EventResponse
import com.drians.kade.football.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventPresenter(private val view: EventView,
                     private val apiRepository: ApiRepository,
                     private val  gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()){

    // next match
    fun getEventNext(leagueId: String){
        view.showLoading()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository.doRequestAsync(
                TheSportDBApi.getEventNext(leagueId)).await(), EventResponse::class.java)

            view.hideLoading()
            try {
                data.events?.let { view.showEventList(it) }
            } catch (e : NullPointerException) {
                view.showEmptyData()
            }

        }
    }

    // previous match
    fun getEventPrevious(leagueId: String){
        view.showLoading()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository.doRequestAsync(
                TheSportDBApi.getEventPrevious(leagueId)).await(), EventResponse::class.java)

            view.hideLoading()
            try {
                data.events?.let { view.showEventList(it) }
            } catch (e : NullPointerException) {
                view.showEmptyData()
            }
        }
    }
}