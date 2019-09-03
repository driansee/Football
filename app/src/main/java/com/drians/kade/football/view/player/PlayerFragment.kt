package com.drians.kade.football.view.player

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.drians.kade.football.R
import com.drians.kade.football.adapter.PlayerAdapter
import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.model.Player
import com.drians.kade.football.util.invisible
import com.drians.kade.football.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PlayerFragment : Fragment(), AnkoComponent<Context>, PlayerView {

    private var player: MutableList<Player> = mutableListOf()
    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter: PlayerAdapter
    private lateinit var listPlayer: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var id: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        id = activity?.intent?.getStringExtra("id").toString()

        adapter = PlayerAdapter(player){
            context?.startActivity<PlayerDetailActivity>("id" to "${it.playerId}")
        }
        listPlayer.adapter = adapter

        presenter = PlayerPresenter(this, ApiRepository(), Gson())
        presenter.getPlayerList(id)
        swipeRefresh.onRefresh { presenter.getPlayerList(id) }
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

                    listPlayer = recyclerView {
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

    override fun showPlayerList(data: List<Player>) {
        swipeRefresh.isRefreshing = false
        player.clear()
        player.addAll(data)
        adapter.notifyDataSetChanged()
    }
}