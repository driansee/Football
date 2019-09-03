package com.drians.kade.football.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.*
import android.widget.*
import com.drians.kade.football.R
import com.drians.kade.football.R.id.*
import com.drians.kade.football.model.FavoriteTeam
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class FavoriteTeamAdapter(private val favorite: List<FavoriteTeam>, private val listener: (FavoriteTeam) -> Unit)
    : RecyclerView.Adapter<FavoriteTeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTeamViewHolder {
        return FavoriteTeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: FavoriteTeamViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size

}

class TeamUI : AnkoComponent<ViewGroup> {
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

class FavoriteTeamViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val teamBadge: ImageView = view.find(image_team_badge)
    private val teamName: TextView = view.find(text_team_name)
    private val teamDescription: TextView = view.find(text_team_description)

    fun bindItem(favorite: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
        Picasso.get()
            .load(favorite.teamBadge)
            .placeholder(R.drawable.img_placeholder_team)
            .error(R.drawable.img_placeholder_team)
            .into(teamBadge)
        teamName.text = favorite.teamName
        teamDescription.text = favorite.teamDescriptionEn
        itemView.setOnClickListener { listener(favorite) }
    }
}