package com.drians.kade.football.view.match.event

import com.drians.kade.football.view.match.EventDetailPresenter
import com.drians.kade.football.view.match.EventDetailView
import com.drians.kade.football.TestContextProvider
import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.model.Event
import com.drians.kade.football.model.EventResponse
import com.drians.kade.football.model.Team
import com.drians.kade.football.model.TeamResponse
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EventDetailPresenterTest {

    @Mock
    private lateinit var view: EventDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: EventDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter =
            EventDetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getEventDetail() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val idEvent = "609285" // data yang digunakan adalah idEvent 609285 yaitu Parma Calcio 1913 vs Juventus

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", EventResponse::class.java)).thenReturn(response)

            presenter.getEventDetail(idEvent)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEventDetail(events)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getTeamDetail() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val idHomeTeam = "135728" // data yang digunakan adalah idHomeTeam 135728 yaitu Parma Calcio 1913
        val idAwayTeam = "133676" // data yang digunakan adalah idAwayTeam 133676 yaitu Juventus

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", TeamResponse::class.java)).thenReturn(response)

            presenter.getTeamDetail(idHomeTeam, idAwayTeam)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showTeamDetail(teams, teams)
            Mockito.verify(view).hideLoading()
        }
    }
}