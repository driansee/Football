package com.drians.kade.football.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.drians.kade.football.R
import com.drians.kade.football.R.id.*
import com.drians.kade.football.model.Team
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class TeamAdapter (private val teams: List<Team>, private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(TeamAdapterUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount(): Int = teams.size
}

class TeamAdapterUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {

        cardView {
            lparams(matchParent, wrapContent) {
                margin = dip(8)
                cardElevation = 8f
                radius = 4f
            }

            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(12)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = image_team_badge
                }.lparams{
                    height = dip(72)
                    width = dip(72)
                }

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.VERTICAL

                    textView {
                        id = text_team_name
                        textSize = 16f
                        textColor = Color.BLACK
                    }.lparams{
                        topMargin = dip(6)
                        bottomMargin = dip(4)
                        leftMargin = dip(12)
                        rightMargin = dip(12)
                    }

                    textView {
                        id = text_team_description
                        textSize = 12f
                        ellipsize = TextUtils.TruncateAt.END
                        maxLines = 2
                    }.lparams{
                        leftMargin = dip(12)
                        rightMargin = dip(12)
                    }
                }
            }
        }
    }
}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val teamBadge: ImageView = view.find(image_team_badge)
    private val teamName: TextView = view.find(text_team_name)
    private val teamDescription: TextView = view.find(text_team_description)

    fun bindItem(teams: Team, listener: (Team) -> Unit) {
        Picasso.get()
            .load(teams.teamBadge)
            .placeholder(R.drawable.img_placeholder_team)
            .error(R.drawable.img_placeholder_team)
            .into(teamBadge)
        teamName.text = teams.teamName
        teamDescription.text = teams.teamDescriptionEn
        itemView.setOnClickListener { listener(teams) }
    }
}