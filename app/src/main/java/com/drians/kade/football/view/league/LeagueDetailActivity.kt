package com.drians.kade.football.view.league

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.drians.kade.football.R
import com.drians.kade.football.util.*
import com.drians.kade.football.adapter.PagerLeagueAdapter
import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.model.League
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_league_detail.*


class LeagueDetailActivity: AppCompatActivity(), LeagueDetailView {

    private lateinit var presenter: LeagueDetailPresenter
    private lateinit var leagues: League
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState )
        setContentView(R.layout.activity_league_detail)

        supportActionBar?.elevation = 0F
        supportActionBar?.title = getString(R.string.title_bar_league_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        id = intent.getStringExtra("id")

        presenter = LeagueDetailPresenter(this, ApiRepository(), Gson())
        presenter.getLeagueDetail(id)

        view_pager.adapter = PagerLeagueAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
       progressBar.invisible()
    }

    override fun showLeagueDetail(data: List<League>) {
        leagues = League(
            data[0].leagueId, data[0].mLeagueName,
            data[0].mLeagueBadge, data[0].leagueName, data[0].leagueSport,
            data[0].leagueFormedYear, data[0].leagueCountry,
            data[0].leagueBadge, data[0].leagueDescriptionEN
        )

        Picasso.get().load(data[0].leagueBadge).into(image_badge)
        text_name.text = data[0].leagueName
        text_sport.text = data[0].leagueSport
        text_formed_year.text = data[0].leagueFormedYear
        text_country.text = data[0].leagueCountry
        text_description.text = data[0].leagueDescriptionEN
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}