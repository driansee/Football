package com.drians.kade.football.view.league

import com.drians.kade.football.TestContextProvider
import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.model.League
import com.drians.kade.football.model.LeagueResponse
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LeagueDetailPresenterTest {

    @Mock
    private lateinit var view: LeagueDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: LeagueDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LeagueDetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getLeagueDetail() {
        val leagues: MutableList<League> = mutableListOf()
        val response = LeagueResponse(leagues)
        val leagueId = "4332" // data yang digunakan adalah id 4332 yaitu Italian Serie A

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", LeagueResponse::class.java)).thenReturn(response)

            presenter.getLeagueDetail(leagueId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showLeagueDetail(leagues)
            Mockito.verify(view).hideLoading()
        }
    }
}