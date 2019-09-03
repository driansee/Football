package com.drians.kade.football.view.favorite

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.drians.kade.football.R
import com.drians.kade.football.adapter.FavoriteMatchAdapter
import com.drians.kade.football.database.database
import com.drians.kade.football.view.match.EventDetailActivity
import com.drians.kade.football.model.FavoriteMatch
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteMatchFragment : Fragment(), AnkoComponent<Context> {

    private var favorites: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var adapter: FavoriteMatchAdapter
    private lateinit var listFavoriteMatch: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteMatchAdapter(favorites){
            context?.startActivity<EventDetailActivity>("id" to "${it.eventId}",
                "idHomeTeam" to "${it.homeTeamId}", "idAwayTeam" to "${it.awayTeamId}")
        }

        listFavoriteMatch.adapter = adapter
        swipeRefresh.onRefresh {
            showFavorite()
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    // fungsi untuk menampilkan data tim favorite
    private fun showFavorite(){
        favorites.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.elevation = 0f
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                listFavoriteMatch = recyclerView {
                    lparams (width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }
}