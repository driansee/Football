package com.drians.kade.football.view.player

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.drians.kade.football.R
import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.model.Player
import com.drians.kade.football.util.changeFormatDate
import com.drians.kade.football.util.invisible
import com.drians.kade.football.util.strTodate
import com.drians.kade.football.util.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_detail.*
import org.jetbrains.anko.support.v4.onRefresh

class PlayerDetailActivity : AppCompatActivity(), PlayerDetailView {

    private lateinit var presenter: PlayerDetailPresenter
    private lateinit var players: Player
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        id = intent.getStringExtra("id")

        presenter = PlayerDetailPresenter(this, ApiRepository(), Gson())
        presenter.getPlayerDetail(id)

        swipeRefresh.setColorSchemeResources(
            R.color.colorAccent,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
        swipeRefresh.onRefresh { presenter.getPlayerDetail(id) }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    @SuppressLint("SimpleDateFormat")
    override fun showPlayerDetail(data: List<Player>) {
        players = Player(
            data[0].playerId, data[0].playerName, data[0].playerCutout,
            data[0].playerDescriptionEn, data[0].playerBirthLocation,
            data[0].playerDateBorn, data[0].playerHeight, data[0].playerWeight,
            data[0].playerGender, data[0].playerNationality, data[0].playerTeam,
            data[0].playerTeamNational, data[0].playerSide, data[0].playerPosition,
            data[0].playerNumber, data[0].playerDateSigned, data[0].playerSigning,
            data[0].playerKit, data[0].playerWage, data[0].playerFanart1
        )
        swipeRefresh.isRefreshing = false

        // change date to SimpleDateFormat
        val date = strTodate(data[0].playerDateBorn)

        text_player_name.text = data[0].playerName
        text_player_description.text = data[0].playerDescriptionEn
        text_player_birth_location.text = data[0].playerBirthLocation
        text_player_born.text = changeFormatDate(date)
        text_player_height.text = data[0].playerHeight
        text_player_weight.text = data[0].playerWeight
        text_player_gender.text = data[0].playerGender
        text_player_nationality.text = data[0].playerNationality
        text_player_team.text = data[0].playerTeam
        text_player_team_national.text = data[0].playerTeamNational
        text_player_side.text = data[0].playerSide
        text_player_position.text = data[0].playerPosition
        text_player_number.text = data[0].playerNumber
        text_player_date_signed.text = data[0].playerDateSigned
        text_player_signing.text = data[0].playerSigning
        text_player_kit.text = data[0].playerKit
        text_player_wage.text = data[0].playerWage
        Picasso.get().load(data[0].playerFanart1).into(image_player_fanart)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
