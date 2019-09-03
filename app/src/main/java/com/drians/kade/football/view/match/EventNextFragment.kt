package com.drians.kade.football.view.match


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.*
import com.drians.kade.football.R
import com.drians.kade.football.adapter.EventAdapter
import com.drians.kade.football.util.*
import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.model.Event
import com.drians.kade.football.view.search.SearchEventActivity
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class EventNextFragment : Fragment(), AnkoComponent<Context>, EventView {

    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: EventPresenter
    private lateinit var adapter: EventAdapter
    private lateinit var listNextMatch: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var emptyDataView: LinearLayout

    private lateinit var id: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        id = activity?.intent?.getStringExtra("id").toString()

        adapter = EventAdapter(events) {
            context?.startActivity<EventDetailActivity>("id" to "${it.eventId}",
                "idHomeTeam" to "${it.homeTeamId}", "idAwayTeam" to "${it.awayTeamId}")
        }
        listNextMatch.adapter = adapter

        presenter = EventPresenter(this, ApiRepository(), Gson())
        presenter.getEventNext(id)
        swipeRefresh.onRefresh { presenter.getEventNext(id)}

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(matchParent, matchParent)
            orientation = LinearLayout.VERTICAL

            linearLayout {
                lparams(matchParent, wrapContent)
                orientation = LinearLayout.VERTICAL
                padding = dip(12)

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(
                        R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light
                    )

                    scrollView {
                        lparams(matchParent, matchParent)
                        isVerticalScrollBarEnabled = false
                        isNestedScrollingEnabled = true

                        relativeLayout {
                            lparams(matchParent, wrapContent)

                            emptyDataView = linearLayout {
                                orientation = LinearLayout.VERTICAL

                                textView {
                                    gravity = Gravity.CENTER
                                    padding = dip(8)
                                    textResource = R.string.text_match_not_found
                                }
                            }.lparams { centerInParent() }

                            listNextMatch = recyclerView {
                                lparams(matchParent, wrapContent)
                                layoutManager = LinearLayoutManager(context)
                            }

                            progressBar = progressBar { }.lparams{centerInParent()}
                        }
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
        listNextMatch.invisible()
        emptyDataView.invisible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        listNextMatch.visible()
        emptyDataView.invisible()
    }

    override fun showEmptyData() {
        progressBar.invisible()
        listNextMatch.invisible()
        emptyDataView.visible()
        swipeRefresh.isRefreshing = false
    }

    override fun showEventList(data: List<Event>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_menu, menu)

        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView?

        searchView?.queryHint = "Search Matches"

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                startActivity<SearchEventActivity>("query" to query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }
}
