package com.drians.kade.football.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.*
import android.widget.*
import com.drians.kade.football.R
import com.drians.kade.football.R.id.*
import com.drians.kade.football.model.League
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.cardview.v7.themedCardView


class LeagueAdapter (private val leagues: List<League>, private val listener: (League) -> Unit)
    : RecyclerView.Adapter<LeagueViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        return LeagueViewHolder(
            LeagueUI().createView(AnkoContext.create(parent.context, parent))
        )
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(leagues[position], listener)
    }

    override fun getItemCount(): Int = leagues.size

}

class LeagueUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        cardView {
            lparams(matchParent, wrapContent) {
                margin = dip(12)
                cardElevation = 12f
                radius = 8f
            }

            verticalLayout {
                lparams(matchParent, wrapContent)

                frameLayout {
                    lparams(matchParent, wrapContent)
                    padding = dip(12)
                    //backgroundColor = ContextCompat.getColor(context, colorBackground)

                    imageView { id = image_league_badge }.lparams(dip(68), dip(68)) {
                        gravity = Gravity.CENTER
                    }
                }

                textView {
                    id = text_league_name
                    lines = 2
                    textColor = Color.BLACK
                    gravity = Gravity.CENTER
                }.lparams(matchParent, wrapContent) {
                    margin = dip(12)
                }
            }
        }
    }
}

class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val badge: ImageView = view.find(image_league_badge)
    private val name: TextView = view.find(text_league_name)

    fun bindItem(leagues: League, listener: (League) -> Unit) {
        name.text = leagues.mLeagueName
        leagues.mLeagueBadge?.let { Picasso.get().load(it).into(badge) }
        itemView.setOnClickListener { listener(leagues) }
    }
}