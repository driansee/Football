package com.drians.kade.football.view.standings

import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.api.TheSportDBApi
import com.drians.kade.football.model.TableResponse
import com.drians.kade.football.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TablePresenter(private val view: TableView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTableList(idLeague: String?) {
        view.showLoading()

        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                .doRequestAsync(TheSportDBApi.getTableList(idLeague)).await(),
                TableResponse::class.java)

            view.showTableList(data.table)
            view.hideLoading()
        }
    }
}