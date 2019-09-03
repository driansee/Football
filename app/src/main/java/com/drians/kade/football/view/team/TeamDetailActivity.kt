package com.drians.kade.football.view.team

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.drians.kade.football.R
import com.drians.kade.football.adapter.PagerTeamAdapter
import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.database.database
import com.drians.kade.football.model.FavoriteTeam
import com.drians.kade.football.model.Team
import com.drians.kade.football.util.invisible
import com.drians.kade.football.util.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.design.snackbar

class TeamDetailActivity : AppCompatActivity(), TeamDetailView {

    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: Team
    private lateinit var id: String

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        supportActionBar?.elevation = 0F
        supportActionBar?.title = getString(R.string.title_bar_team_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        id = intent.getStringExtra("id")

        favoriteState()
        presenter = TeamDetailPresenter(this, ApiRepository(), Gson())
        presenter.getTeamDetail(id)

        view_pager.adapter = PagerTeamAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
    }

    // fungsi bantuan untuk mengecek status apakah sebuah tim sudah masuk ke dalam database atau belum
    private fun favoriteState(){
        database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                .whereArgs("(TEAM_ID = {id})", "id" to id)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (favorite.isNotEmpty()) isFavorite = true
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamDetail(data: List<Team>) {
        teams = Team(
            data[0].teamId, data[0].teamName, data[0].teamBadge, data[0].teamDescriptionEn
        )

        text_team_name.text = data[0].teamName
        Picasso.get().load(data[0].teamBadge).into(image_badge)
        text_team_formed_year.text = data[0].teamFormedYear
    }

    // fungsi untuk memasukkan data ke dalam database
    private fun addToFavorite(){
        try {
            database.use {
                insert(
                    FavoriteTeam.TABLE_FAVORITE_TEAM,
                    FavoriteTeam.TEAM_ID to teams.teamId,
                    FavoriteTeam.TEAM_NAME to teams.teamName,
                    FavoriteTeam.TEAM_BADGE to teams.teamBadge,
                    FavoriteTeam.TEAM_DESCRIPTION_EN to teams.teamDescriptionEn)
            }
            view_pager.snackbar("Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            view_pager.snackbar(e.localizedMessage).show()
        }
    }

    // fungsi menghapus data dari database favorite
    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoriteTeam.TABLE_FAVORITE_TEAM, "(TEAM_ID = {id})",
                    "id" to id)
            }
            view_pager.snackbar("Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            view_pager.snackbar(e.localizedMessage).show()
        }
    }

    // fungsi untuk mengatur ikon pada tombol Favorite
    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites_white_24dp)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites_white_24dp)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}