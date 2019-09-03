package com.drians.kade.football.view.search

import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.api.TheSportDBApi
import com.drians.kade.football.model.EventSearchResponse
import com.drians.kade.football.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchEventPresenter(private val view: SearchEventView,
                           private val apiRepository: ApiRepository,
                           private val  gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getEventSearch(eventName: String){
        view.showLoading()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository.doRequestAsync(
                TheSportDBApi.getEventSearch(eventName)).await(), EventSearchResponse::class.java)

            view.hideLoading()
            try {
                data.event?.let { view.showEventList(it) }
            } catch (e : NullPointerException) {
                view.showEmptyData()
            }
        }
    }
}
