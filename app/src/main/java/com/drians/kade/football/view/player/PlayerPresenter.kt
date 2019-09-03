package com.drians.kade.football.view.player

import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.api.TheSportDBApi
import com.drians.kade.football.model.PlayerResponse
import com.drians.kade.football.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerPresenter(private val view: PlayerView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerList(idTeam: String?) {
        view.showLoading()

        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                .doRequestAsync(TheSportDBApi.getPlayerList(idTeam)).await(),
                PlayerResponse::class.java)

            view.showPlayerList(data.player)
            view.hideLoading()
        }
    }
}