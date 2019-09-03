package com.drians.kade.football.view.favorite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.drians.kade.football.R
import com.drians.kade.football.adapter.PagerSearchAdapter
import kotlinx.android.synthetic.main.activity_league_detail.view.*


class FavoriteFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.view_pager.adapter = PagerSearchAdapter(childFragmentManager)
        view.tab_layout.setupWithViewPager(view.view_pager)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }
}
