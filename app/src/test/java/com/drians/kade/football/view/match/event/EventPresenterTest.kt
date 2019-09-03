package com.drians.kade.football.view.match.event

import com.drians.kade.football.view.match.EventPresenter
import com.drians.kade.football.view.match.EventView
import com.drians.kade.football.TestContextProvider
import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.model.Event
import com.drians.kade.football.model.EventResponse
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EventPresenterTest {

    @Mock
    private lateinit var view: EventView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: EventPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EventPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getEventNext() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val leagueId = "4332" // data yang digunakan adalah id 4332 yaitu Italian Serie A

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", EventResponse::class.java)).thenReturn(response)

            presenter.getEventNext(leagueId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEventList(events)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getEventPrevious() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val leagueId = "4332" // data yang digunakan adalah id 4332 yaitu Italian Serie A

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", EventResponse::class.java)).thenReturn(response)

            presenter.getEventPrevious(leagueId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEventList(events)
            Mockito.verify(view).hideLoading()
        }
    }
}