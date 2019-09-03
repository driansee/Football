package com.drians.kade.football.view.team

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.drians.kade.football.R
import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.model.Team
import com.drians.kade.football.util.invisible
import com.drians.kade.football.util.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamInfoFragment : Fragment(), AnkoComponent<Context>, TeamDetailView {

    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: Team
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var id: String
    private lateinit var teamStadium: TextView
    private lateinit var teamStadiumThumb: ImageView
    private lateinit var teamStadiumLocation: TextView
    private lateinit var teamStadiumCapacity: TextView
    private lateinit var teamStadiumDescription: TextView
    private lateinit var teamDescription: TextView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        id = activity?.intent?.getStringExtra("id").toString()

        presenter = TeamDetailPresenter(this, ApiRepository(), Gson())
        presenter.getTeamDetail(id)
        swipeRefresh.onRefresh { presenter.getTeamDetail(id) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE
            padding = dip(12)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                scrollView {
                    isVerticalScrollBarEnabled = false
                    isNestedScrollingEnabled = true

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        linearLayout {
                            lparams(matchParent, wrapContent)
                            orientation = LinearLayout.VERTICAL

                            cardView {
                                lparams(matchParent, wrapContent) {
                                    cardElevation = 8f
                                    radius = 12f
                                    useCompatPadding = true
                                }

                                linearLayout {
                                    lparams(matchParent, wrapContent)
                                    orientation = LinearLayout.VERTICAL
                                    padding = dip(12)

                                    teamDescription = textView {
                                        textSize = 12f
                                        setLineSpacing(12f, 1f)
                                    }.lparams(matchParent, wrapContent)
                                }
                            }

                            cardView {
                                lparams(matchParent, wrapContent) {
                                    cardElevation = 8f
                                    radius = 12f
                                    useCompatPadding = true
                                }

                                linearLayout {
                                    lparams(matchParent, wrapContent)
                                    orientation = LinearLayout.VERTICAL

                                    frameLayout {
                                        teamStadiumThumb = imageView {
                                            scaleType = ImageView.ScaleType.CENTER_CROP
                                        }.lparams(matchParent, dip(200))
                                    }

                                    teamStadium = textView {
                                        gravity = Gravity.CENTER
                                        textColor = Color.BLACK
                                        textSize = 16f
                                        topPadding = dip(12)
                                    }

                                    teamStadiumLocation = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 12f
                                        textColorResource = R.color.colorAccent
                                    }

                                    teamStadiumCapacity = textView {
                                        gravity = Gravity.CENTER
                                        textSize = 12f
                                    }

                                    view{
                                        backgroundColorResource = android.R.color.darker_gray
                                    }.lparams(matchParent, dip(1)) {
                                        topMargin = dip(12)
                                        leftMargin = dip(12)
                                        rightMargin = dip(12)
                                    }

                                    teamStadiumDescription = textView {
                                        textSize = 12f
                                        padding = dip(12)
                                        setLineSpacing(12f, 1f)
                                    }.lparams(matchParent, wrapContent)
                                }
                            }
                        }
                        progressBar = progressBar {}.lparams { centerHorizontally() }
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

    override fun showTeamDetail(data: List<Team>) {
        teams = Team(
            data[0].teamId, data[0].teamName, data[0].teamBadge, data[0].teamDescriptionEn
        )
        swipeRefresh.isRefreshing = false

        teamDescription.text = data[0].teamDescriptionEn
        teamStadium.text = data[0].teamStadium
        Picasso.get().load(data[0].teamStadiumThumb).into(teamStadiumThumb)
        teamStadiumLocation.text = data[0].teamStadiumLocation
        teamStadiumCapacity.text = data[0].teamStadiumCapacity
        teamStadiumDescription.text = data[0].teamStadiumDescription
    }
}