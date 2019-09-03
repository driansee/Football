package com.drians.kade.football.view.league

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.view.*
import com.drians.kade.football.R
import com.drians.kade.football.adapter.LeagueAdapter
import com.drians.kade.football.model.League
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class LeagueFragment : Fragment(), AnkoComponent<Context> {

    private var leagues: MutableList<League> = mutableListOf()
    private lateinit var listLeague: RecyclerView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listLeague.adapter = LeagueAdapter(leagues) {
            context?.startActivity<LeagueDetailActivity>("id" to "${it.leagueId}")
        }

        // inisiasi data
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        relativeLayout {
            listLeague = recyclerView {
                layoutManager = GridLayoutManager(context, 2)
                lparams(matchParent, matchParent)
            }
        }
    }

    private fun initData(){

        val id = resources.getStringArray(R.array.array_league_id)
        val name = resources.getStringArray(R.array.array_league_name)
        val logo = resources.obtainTypedArray(R.array.array_league_image)

        leagues.clear()

        for (i in id.indices) {
            leagues.add(League(id[i], name[i], logo.getResourceId(i, 0)))
        }
        //Recycle the typed array
        logo.recycle()
    }
}