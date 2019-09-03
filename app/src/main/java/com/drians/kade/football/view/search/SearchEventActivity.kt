package com.drians.kade.football.view.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.drians.kade.football.view.match.EventDetailActivity
import com.drians.kade.football.R
import com.drians.kade.football.adapter.EventAdapter
import com.drians.kade.football.util.*
import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.model.Event
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class SearchEventActivity : AppCompatActivity(), SearchEventView {

    private var event: MutableList<Event> = mutableListOf()
    private lateinit var adapter: EventAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var listSearchMatch: RecyclerView
    private lateinit var presenter: SearchEventPresenter
    private lateinit var emptyDataView: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Search Event Activity UI
        scrollView {
            lparams(matchParent, matchParent)
            isVerticalScrollBarEnabled = false
            padding = dip(12)

            relativeLayout {
                lparams(matchParent, wrapContent)

                emptyDataView = linearLayout {
                    orientation = LinearLayout.VERTICAL

                    textView {
                        gravity = Gravity.CENTER
                        padding = dip(8)
                        textResource = R.string.text_match_not_found
                    }
                }.lparams {
                    centerInParent()
                }

                listSearchMatch = recyclerView {
                    id = R.id.list_search_match
                    lparams(matchParent, wrapContent)
                    layoutManager = LinearLayoutManager(context)
                }

                progressBar = progressBar { }.lparams{centerInParent()}
            }
        }

        // get string extra from event next & previous fragment
        val query = intent.getStringExtra("query")

        supportActionBar?.title = "$query matches"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = EventAdapter(event) {
            startActivity<EventDetailActivity>("id" to "${it.eventId}",
                "idHomeTeam" to "${it.homeTeamId}", "idAwayTeam" to "${it.awayTeamId}")
        }
        listSearchMatch.adapter = adapter

        presenter = SearchEventPresenter(this, ApiRepository(), Gson())
        presenter.getEventSearch(query)
    }

    override fun showLoading() {
        progressBar.visible()
        listSearchMatch.invisible()
        emptyDataView.invisible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        listSearchMatch.visible()
        emptyDataView.invisible()
    }

    override fun showEmptyData() {
        progressBar.invisible()
        listSearchMatch.invisible()
        emptyDataView.visible()
    }

    override fun showEventList(data: List<Event>) {
        event.clear()
        event.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}