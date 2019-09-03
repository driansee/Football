package com.drians.kade.football.view.team

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.drians.kade.football.R
import com.drians.kade.football.adapter.TeamAdapter
import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.model.Team
import com.drians.kade.football.view.search.SearchTeamActivity
import com.drians.kade.football.util.invisible
import com.drians.kade.football.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamFragment : Fragment(), AnkoComponent<Context>, TeamView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamsPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var id: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        id = activity?.intent?.getStringExtra("id").toString()

        adapter = TeamAdapter(teams){
            context?.startActivity<TeamDetailActivity>("id" to "${it.teamId}")
        }
        listTeam.adapter = adapter

        presenter = TeamsPresenter(this, ApiRepository(), Gson())
        presenter.getTeamList(id)
        swipeRefresh.onRefresh { presenter.getTeamList(id) }

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            padding = dip(12)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listTeam = recyclerView {
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(context)
                    }

                    progressBar = progressBar {
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_menu, menu)

        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView?

        searchView?.queryHint = "Search Teams"

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                startActivity<SearchTeamActivity>("query" to query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }
}