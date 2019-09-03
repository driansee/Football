package com.drians.kade.football.view.favorite

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.drians.kade.football.R
import com.drians.kade.football.adapter.FavoriteTeamAdapter
import com.drians.kade.football.database.database
import com.drians.kade.football.model.FavoriteTeam
import com.drians.kade.football.view.team.TeamDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*

class FavoriteTeamFragment : Fragment(), AnkoComponent<Context> {

    private var favorites: MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var adapter: FavoriteTeamAdapter
    private lateinit var listFavoriteTeam: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteTeamAdapter(favorites){
            context?.startActivity<TeamDetailActivity>("id" to "${it.teamId}")
        }

        listFavoriteTeam.adapter = adapter
        swipeRefresh.onRefresh { showFavorite() }
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
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
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

                listFavoriteTeam = recyclerView {
                    lparams (width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }
}