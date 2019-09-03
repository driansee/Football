package com.drians.kade.football.view.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.drians.kade.football.R
import com.drians.kade.football.adapter.TeamAdapter
import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.model.Team
import com.drians.kade.football.view.team.TeamDetailActivity
import com.drians.kade.football.util.invisible
import com.drians.kade.football.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class SearchTeamActivity : AppCompatActivity(), SearchTeamView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var adapter: TeamAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var listSearchTeam: RecyclerView
    private lateinit var presenter: SearchTeamPresenter
    private lateinit var emptyDataView: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Search Team Activity UI
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
                        padding = dip(12)
                        textResource = R.string.text_team_not_found
                    }
                }.lparams {
                    centerInParent()
                }

                listSearchTeam = recyclerView {
                    id = R.id.list_search_team
                    lparams(matchParent, wrapContent)
                    layoutManager = LinearLayoutManager(context)
                }

                progressBar = progressBar { }.lparams{centerInParent()}
            }
        }

        // get string extra from team fragment
        val query = intent.getStringExtra("query")

        supportActionBar?.title = "$query teams"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = TeamAdapter(teams) {
            startActivity<TeamDetailActivity>("id" to "${it.teamId}")
        }
        listSearchTeam.adapter = adapter

        presenter = SearchTeamPresenter(this, ApiRepository(), Gson())
        presenter.getTeamSearch(query)
    }

    override fun showLoading() {
        progressBar.visible()
        listSearchTeam.invisible()
        emptyDataView.invisible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        listSearchTeam.visible()
        emptyDataView.invisible()
    }

    override fun showEmptyData() {
        progressBar.invisible()
        listSearchTeam.invisible()
        emptyDataView.visible()
    }

    override fun showTeamtList(data: List<Team>) {
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}