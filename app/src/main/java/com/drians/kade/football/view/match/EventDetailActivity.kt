package com.drians.kade.football.view.match

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import android.widget.*
import com.drians.kade.football.R
import com.drians.kade.football.util.*
import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.database.database
import com.drians.kade.football.model.Event
import com.drians.kade.football.model.FavoriteMatch
import com.drians.kade.football.model.Team
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import java.text.SimpleDateFormat

class EventDetailActivity : AppCompatActivity(), EventDetailView {

    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var presenter: EventDetailPresenter
    private lateinit var events: Event
    private lateinit var textEventName: TextView
    private lateinit var textEventDate: TextView
    private lateinit var textEventTime: TextView
    private lateinit var imageHomeBadge: ImageView
    private lateinit var textHomeTeam: TextView
    private lateinit var textHomeScore: TextView
    private lateinit var textHomeGoalDetails: TextView
    private lateinit var textHomeRedCards: TextView
    private lateinit var textHomeYellowCards: TextView
    private lateinit var textHomeLineupGoalkeeper: TextView
    private lateinit var textHomeLineupDefense: TextView
    private lateinit var textHomeLineupMidfield: TextView
    private lateinit var textHomeLineupForward: TextView
    private lateinit var textHomeLineupSubstitutes: TextView
    private lateinit var textHomeShots: TextView
    private lateinit var imageAwayBadge: ImageView
    private lateinit var textAwayTeam: TextView
    private lateinit var textAwayScore: TextView
    private lateinit var textAwayGoalDetails: TextView
    private lateinit var textAwayRedCards: TextView
    private lateinit var textAwayYellowCards: TextView
    private lateinit var textAwayLineupGoalkeeper: TextView
    private lateinit var textAwayLineupDefense: TextView
    private lateinit var textAwayLineupMidfield: TextView
    private lateinit var textAwayLineupForward: TextView
    private lateinit var textAwayLineupSubstitutes: TextView
    private lateinit var textAwayShots: TextView

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var id: String
    private lateinit var idHomeTeam: String
    private lateinit var idAwayTeam: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Event Detail Activity UI
        linearLayout {
            lparams(matchParent, matchParent)
            orientation = LinearLayout.VERTICAL
            backgroundColorResource = android.R.color.white

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

                    relativeLayout {
                        lparams(matchParent, wrapContent)

                        linearLayout {
                            lparams(matchParent, wrapContent)
                            orientation = LinearLayout.VERTICAL

                            linearLayout {
                                orientation = LinearLayout.VERTICAL
                                backgroundColorResource = R.color.colorPrimary
                                lparams(matchParent, wrapContent)

                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    lparams(matchParent, wrapContent)

                                    linearLayout {
                                        orientation = LinearLayout.VERTICAL
                                        gravity = Gravity.CENTER
                                        lparams(0, wrapContent) {
                                            leftMargin = dip(20)
                                            topMargin = dip(20)
                                            bottomMargin = dip(20)
                                            weight = 3f
                                        }

                                        imageHomeBadge = imageView {
                                        }.lparams(dip(90), dip(90))

                                        textHomeTeam = textView {
                                            typeface = Typeface.SERIF
                                            gravity = Gravity.CENTER
                                            textColorResource = android.R.color.white
                                        }.lparams(wrapContent, wrapContent) {
                                            topMargin = dip(12)
                                        }

                                    }

                                    linearLayout {
                                        orientation = LinearLayout.VERTICAL
                                        lparams(0, wrapContent) {
                                            gravity = Gravity.CENTER
                                            weight = 0.5f
                                        }

                                        textHomeScore = textView {
                                            textSize = 32f
                                            typeface = Typeface.DEFAULT_BOLD
                                            textColorResource = android.R.color.white
                                        }.lparams(wrapContent, wrapContent)
                                    }

                                    linearLayout {
                                        orientation = LinearLayout.VERTICAL
                                        gravity = Gravity.CENTER_HORIZONTAL
                                        lparams(0, wrapContent) {
                                            gravity = Gravity.CENTER
                                            weight = 1f
                                        }

                                        textView {
                                            textResource = R.string.text_vs
                                            textSize = 18f
                                            typeface = Typeface.DEFAULT_BOLD
                                            textColorResource = android.R.color.white
                                        }.lparams(wrapContent, wrapContent)
                                    }

                                    linearLayout {
                                        orientation = LinearLayout.VERTICAL
                                        lparams(0, wrapContent) {
                                            gravity = Gravity.CENTER
                                            weight = 0.5f
                                        }

                                        textAwayScore = textView {
                                            textSize = 32f
                                            typeface = Typeface.DEFAULT_BOLD
                                            textColorResource = android.R.color.white
                                        }.lparams(wrapContent, wrapContent)
                                    }

                                    linearLayout {
                                        orientation = LinearLayout.VERTICAL
                                        gravity = Gravity.CENTER
                                        lparams(0, wrapContent) {
                                            rightMargin = dip(20)
                                            topMargin = dip(20)
                                            bottomMargin = dip(20)
                                            weight = 3f
                                        }

                                        imageAwayBadge = imageView {
                                        }.lparams(dip(90), dip(90))

                                        textAwayTeam = textView {
                                            typeface = Typeface.SERIF
                                            gravity = Gravity.CENTER
                                            textColorResource = android.R.color.white
                                        }.lparams(wrapContent, wrapContent) {
                                            topMargin = dip(12)
                                        }
                                    }
                                }
                            }

                            linearLayout {
                                orientation = LinearLayout.VERTICAL
                                lparams(matchParent, wrapContent) {
                                    leftMargin = dip(8)
                                    rightMargin = dip(8)
                                }

                                textEventName = textView {
                                    textSize = 18f
                                    textColorResource = android.R.color.black
                                    gravity = Gravity.CENTER
                                }.lparams(wrapContent, wrapContent) {
                                    topMargin = dip(12)
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }

                                textEventDate = textView {
                                }.lparams(wrapContent, wrapContent) {
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    topMargin = dip(4)
                                }

                                textEventTime = textView {
                                }.lparams(wrapContent, wrapContent) {
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    topMargin = dip(4)
                                }

                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    lparams(matchParent, wrapContent){topMargin = (144)}

                                    textHomeShots = textView {
                                        textSize = 12f
                                    }.lparams(0, wrapContent) { weight = 2f }

                                    textView {
                                        textResource = R.string.text_shots
                                        textColorResource = R.color.colorPrimary
                                        textSize = 12f
                                    }.lparams(0, wrapContent) {
                                        leftMargin = dip(4)
                                        rightMargin = dip(4)
                                        weight = 1f
                                    }

                                    textAwayShots = textView {
                                        textSize = 12f
                                        textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                                    }.lparams(0, wrapContent) { weight = 2f }
                                }

                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    lparams(matchParent, wrapContent) { topMargin = dip(12) }

                                    textHomeRedCards = textView {
                                        textSize = 12f
                                    }.lparams(0, wrapContent) { weight = 2f }

                                    textView {
                                        textResource = R.string.text_red_cards
                                        textColorResource = R.color.colorPrimary
                                        textSize = 12f
                                    }.lparams(0, wrapContent) {
                                        leftMargin = dip(4)
                                        rightMargin = dip(4)
                                        weight = 1f
                                    }

                                    textAwayRedCards = textView {
                                        textSize = 12f
                                        textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                                    }.lparams(0, wrapContent) { weight = 2f }
                                }

                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    lparams(matchParent, wrapContent) { topMargin = dip(12) }

                                    textHomeYellowCards = textView {
                                        textSize = 12f
                                    }.lparams(0, wrapContent) { weight = 2f }

                                    textView {
                                        textResource = R.string.text_yellow_cards
                                        textColorResource = R.color.colorPrimary
                                        textSize = 12f
                                    }.lparams(0, wrapContent) {
                                        leftMargin = dip(4)
                                        rightMargin = dip(4)
                                        weight = 1f
                                    }

                                    textAwayYellowCards = textView {
                                        textSize = 12f
                                        textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                                    }.lparams(0, wrapContent) { weight = 2f }
                                }

                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    lparams(matchParent, wrapContent) { topMargin = dip(12) }

                                    textHomeGoalDetails = textView {
                                        textSize = 12f
                                    }.lparams(0, wrapContent) { weight = 2f }

                                    textView {
                                        textResource = R.string.text_goals
                                        textColorResource = R.color.colorPrimary
                                        textSize = 12f
                                    }.lparams(0, wrapContent) {
                                        leftMargin = dip(4)
                                        rightMargin = dip(4)
                                        weight = 1f
                                    }

                                    textAwayGoalDetails = textView {
                                        textSize = 12f
                                        textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                                    }.lparams(0, wrapContent) { weight = 2f }
                                }

                                view {
                                    backgroundResource = android.R.color.darker_gray
                                }.lparams(matchParent, dip(1)) {
                                    topMargin = dip(24)
                                    bottomMargin = dip(12)
                                }

                                textView {
                                    textResource = R.string.text_lineups
                                    textSize = 18f
                                    textColorResource = android.R.color.black
                                }.lparams(wrapContent, wrapContent) { gravity = Gravity.CENTER }

                                view {
                                    backgroundResource = android.R.color.darker_gray
                                }.lparams(matchParent, dip(1)) {
                                    topMargin = dip(12)
                                    bottomMargin = dip(12)
                                }

                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    lparams(matchParent, wrapContent) { topMargin = dip(12) }

                                    textHomeLineupGoalkeeper = textView {
                                        textSize = 12f
                                    }.lparams(0, wrapContent) { weight = 2f }

                                    textView {
                                        textResource = R.string.text_goal_keeper
                                        textColorResource = R.color.colorPrimary
                                        textSize = 12f
                                    }.lparams(0, wrapContent) {
                                        leftMargin = dip(4)
                                        rightMargin = dip(4)
                                        weight = 1f
                                    }

                                    textAwayLineupGoalkeeper = textView {
                                        textSize = 12f
                                        textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                                    }.lparams(0, wrapContent) { weight = 2f }
                                }

                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    lparams(matchParent, wrapContent) { topMargin = dip(12) }

                                    textHomeLineupDefense = textView {
                                        textSize = 12f
                                    }.lparams(0, wrapContent) { weight = 2f }

                                    textView {
                                        textResource = R.string.text_defense
                                        textColorResource = R.color.colorPrimary
                                        textSize = 12f
                                    }.lparams(0, wrapContent) {
                                        leftMargin = dip(4)
                                        rightMargin = dip(4)
                                        weight = 1f
                                    }

                                    textAwayLineupDefense = textView {
                                        textSize = 12f
                                        textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                                    }.lparams(0, wrapContent) { weight = 2f }
                                }

                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    lparams(matchParent, wrapContent) { topMargin = dip(12) }

                                    textHomeLineupMidfield = textView {
                                        textSize = 12f
                                    }.lparams(0, wrapContent) { weight = 2f }

                                    textView {
                                        textResource = R.string.text_midfield
                                        textColorResource = R.color.colorPrimary
                                        textSize = 12f
                                    }.lparams(0, wrapContent) {
                                        leftMargin = dip(4)
                                        rightMargin = dip(4)
                                        weight = 1f
                                    }

                                    textAwayLineupMidfield = textView {
                                        textSize = 12f
                                        textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                                    }.lparams(0, wrapContent) { weight = 2f }
                                }

                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    lparams(matchParent, wrapContent) { topMargin = dip(12) }

                                    textHomeLineupForward = textView {
                                        textSize = 12f
                                    }.lparams(0, wrapContent) { weight = 2f }

                                    textView {
                                        textResource = R.string.text_forward
                                        textColorResource = R.color.colorPrimary
                                        textSize = 12f
                                    }.lparams(0, wrapContent) {
                                        leftMargin = dip(4)
                                        rightMargin = dip(4)
                                        weight = 1f
                                    }

                                    textAwayLineupForward = textView {
                                        textSize = 12f
                                        textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                                    }.lparams(0, wrapContent) { weight = 2f }
                                }

                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL
                                    lparams(matchParent, wrapContent) {
                                        topMargin = dip(12)
                                        bottomMargin = dip(12)
                                    }

                                    textHomeLineupSubstitutes = textView {
                                        textSize = 12f
                                    }.lparams(0, wrapContent) { weight = 2f }

                                    textView {
                                        textResource = R.string.text_subtitutes
                                        textColorResource = R.color.colorPrimary
                                        textSize = 12f
                                    }.lparams(0, wrapContent) {
                                        leftMargin = dip(4)
                                        rightMargin = dip(4)
                                        weight = 1f
                                    }

                                    textAwayLineupSubstitutes = textView {
                                        textSize = 12f
                                        textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                                    }.lparams(0, wrapContent) { weight = 2f }
                                }
                            }
                        }
                        progressBar = progressBar { }.lparams { centerInParent() }
                    }
                }
            }
        }

        supportActionBar?.elevation = 0F
        supportActionBar?.title = getString(R.string.title_bar_event_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        id = intent.getStringExtra("id")
        idHomeTeam = intent.getStringExtra("idHomeTeam")
        idAwayTeam = intent.getStringExtra("idAwayTeam")

        favoriteState()
        presenter = EventDetailPresenter(this, ApiRepository(), Gson())
        presenter.getEventDetail(id)
        presenter.getTeamDetail(idHomeTeam, idAwayTeam)

        swipeRefresh.onRefresh {
            presenter.getEventDetail(id)
            presenter.getTeamDetail(idHomeTeam, idAwayTeam)
        }
    }

    // fungsi untuk mengecek status apakah sudah masuk ke dalam database atau belum.
    private fun favoriteState(){
        database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
                .whereArgs("(EVENT_ID = {id})", "id" to id)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (favorite.isNotEmpty()) isFavorite = true
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    @SuppressLint("SimpleDateFormat")
    override fun showEventDetail(data: List<Event>) {
        // harus urut sesuai dengan model Event
        events = Event(
            data[0].leagueId, data[0].eventId, data[0].eventName,
            data[0].eventDate, data[0].eventTime, data[0].homeTeamId,
            data[0].eventHomeTeam, data[0].eventHomeScore, data[0].awayTeamId,
            data[0].eventAwayTeam, data[0].eventAwayScore, data[0].eventHomeGoalDetails,
            data[0].eventHomeRedCards, data[0].eventHomeYellowCards, data[0].eventHomeLineupGoalkeeper,
            data[0].eventHomeLineupDefense, data[0].eventHomeLineupMidfield, data[0].eventHomeLineupForward,
            data[0].eventHomeLineupSubstitutes, data[0].eventHomeShots, data[0].eventAwayGoalDetails,
            data[0].eventAwayRedCards, data[0].eventAwayYellowCards, data[0].eventAwayLineupGoalkeeper,
            data[0].eventAwayLineupDefense, data[0].eventAwayLineupMidfield, data[0].eventAwayLineupForward,
            data[0].eventAwayLineupSubstitutes, data[0].eventAwayShots
        )
        swipeRefresh.isRefreshing = false

        // change date to SimpleDateFormat
        val date = strTodate(data[0].eventDate)
        val dateTime = toGMTFormat(data[0].eventDate, data[0].eventTime)

        textEventName.text = data[0].eventName
        textEventDate.text = changeFormatDate(date)
        textEventTime.text = SimpleDateFormat("HH:mm").format(dateTime)
        textHomeTeam.text = data[0].eventHomeTeam
        textHomeScore.text = data[0].eventHomeScore
        textHomeGoalDetails.text = data[0].eventHomeGoalDetails
        textHomeShots.text = data[0].eventHomeShots
        textHomeRedCards.text = data[0].eventHomeRedCards
        textHomeYellowCards.text = data[0].eventHomeYellowCards
        textHomeLineupGoalkeeper.text = data[0].eventHomeLineupGoalkeeper
        textHomeLineupDefense.text = data[0].eventHomeLineupDefense
        textHomeLineupMidfield.text = data[0].eventHomeLineupMidfield
        textHomeLineupForward.text = data[0].eventHomeLineupForward
        textHomeLineupSubstitutes.text = data[0].eventHomeLineupSubstitutes

        textAwayTeam.text = data[0].eventAwayTeam
        textAwayScore.text = data[0].eventAwayScore
        textAwayGoalDetails.text = data[0].eventAwayGoalDetails
        textAwayShots.text = data[0].eventAwayShots
        textAwayRedCards.text = data[0].eventAwayRedCards
        textAwayYellowCards.text = data[0].eventAwayYellowCards
        textAwayLineupGoalkeeper.text = data[0].eventAwayLineupGoalkeeper
        textAwayLineupDefense.text = data[0].eventAwayLineupDefense
        textAwayLineupMidfield.text = data[0].eventAwayLineupMidfield
        textAwayLineupForward.text = data[0].eventAwayLineupForward
        textAwayLineupSubstitutes.text = data[0].eventAwayLineupSubstitutes
    }

    override fun showTeamDetail(dataHomeTeam: List<Team>, dataAwayTeam: List<Team>) {
        Picasso.get()
            .load(dataHomeTeam[0].teamBadge)
            .into(imageHomeBadge)

        Picasso.get()
            .load(dataAwayTeam[0].teamBadge)
            .into(imageAwayBadge)
    }

    // fungsi untuk memasukkan data ke dalam database
    private fun addToFavorite(){
        try {
            database.use {
                insert(
                    FavoriteMatch.TABLE_FAVORITE_MATCH,
                    FavoriteMatch.EVENT_ID to id,
                    FavoriteMatch.EVENT_NAME to events.eventName,
                    FavoriteMatch.EVENT_DATE to events.eventDate,
                    FavoriteMatch.EVENT_TIME to events.eventTime,
                    FavoriteMatch.EVENT_HOME_TEAM_ID to events.homeTeamId,
                    FavoriteMatch.EVENT_HOME_TEAM to events.eventHomeTeam,
                    FavoriteMatch.EVENT_HOME_SCORE to events.eventHomeScore,
                    FavoriteMatch.EVENT_AWAY_TEAM_ID to events.awayTeamId,
                    FavoriteMatch.EVENT_AWAY_TEAM to events.eventAwayTeam,
                    FavoriteMatch.EVENT_AWAY_SCORE to events.eventAwayScore)
            }
            swipeRefresh.snackbar("Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    // fungsi menghapus data dari database favorite
    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoriteMatch.TABLE_FAVORITE_MATCH, "(EVENT_ID = {id})",
                    "id" to id)
            }
            swipeRefresh.snackbar("Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    // fungsi untuk mengatur ikon pada tombol favorite
    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.
                getDrawable(this, R.drawable.ic_added_to_favorites_white_24dp)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.
                getDrawable(this, R.drawable.ic_add_to_favorites_white_24dp)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
