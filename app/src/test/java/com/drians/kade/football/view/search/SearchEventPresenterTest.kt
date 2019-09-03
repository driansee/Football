package com.drians.kade.football.view.search

import com.drians.kade.football.TestContextProvider
import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.model.Event
import com.drians.kade.football.model.EventSearchResponse
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class SearchEventPresenterTest {

    @Mock
    private lateinit var view: SearchEventView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: SearchEventPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchEventPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getEventSearch() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventSearchResponse(events)
        val strEvent = "juventus" // data yang digunakan adalah nama team sepak bola

        runBlocking {
            Mockito.`when`(apiRepository.doRequestAsync(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", EventSearchResponse::class.java)).thenReturn(response)

            presenter.getEventSearch(strEvent)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEventList(events)
            Mockito.verify(view).hideLoading()
        }
    }
}