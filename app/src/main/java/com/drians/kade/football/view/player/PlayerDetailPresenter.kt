package com.drians.kade.football.view.player

import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.api.TheSportDBApi
import com.drians.kade.football.model.PlayerDetailResponse
import com.drians.kade.football.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerDetailPresenter(private val view: PlayerDetailView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerDetail(idPlayer: String?) {
        view.showLoading()

        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                .doRequestAsync(TheSportDBApi.getPlayerDetail(idPlayer)).await(),
                PlayerDetailResponse::class.java)

            view.showPlayerDetail(data.players)
            view.hideLoading()
        }
    }
}