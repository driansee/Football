package com.drians.kade.football.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.*
import android.widget.*
import com.drians.kade.football.R
import com.drians.kade.football.R.id.*
import com.drians.kade.football.model.Player
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView


class PlayerAdapter (private val player: List<Player>, private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(PlayerAdapterUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(player[position], listener)
    }

    override fun getItemCount(): Int = player.size
}

class PlayerAdapterUI : AnkoComponent<ViewGroup> {
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
                    id = image_player_cutout
                }.lparams{
                    height = dip(72)
                    width = dip(72)
                }

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.VERTICAL

                    textView {
                        id = text_player_name
                        textSize = 16f
                        textColor = Color.BLACK
                    }.lparams{
                        topMargin = dip(6)
                        bottomMargin = dip(4)
                        leftMargin = dip(12)
                        rightMargin = dip(12)
                    }

                    textView {
                        id = text_player_description
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

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val playerCutOut: ImageView = view.find(image_player_cutout)
    private val playerName: TextView = view.find(text_player_name)
    private val playerDescription: TextView = view.find(text_player_description)

    fun bindItem(players: Player, listener: (Player) -> Unit) {
        Picasso.get()
            .load(players.playerCutout)
            .placeholder(R.drawable.img_placeholder_player)
            .error(R.drawable.img_placeholder_player)
            .into(playerCutOut)
        playerName.text = players.playerName
        playerDescription.text = players.playerDescriptionEn
        itemView.setOnClickListener { listener(players) }
    }
}