package com.drians.kade.football.adapter

import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import com.drians.kade.football.R
import com.drians.kade.football.R.id.*
import com.drians.kade.football.model.Table
import org.jetbrains.anko.*


class TableAdapter (private val table: List<Table>) : RecyclerView.Adapter<TableViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        return TableViewHolder(TableUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        holder.bindItem(table[position])
    }

    override fun getItemCount(): Int = table.size
}

class TableUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {

        linearLayout {
            lparams(matchParent, wrapContent)
            orientation = LinearLayout.VERTICAL

            linearLayout {
                lparams(matchParent, wrapContent)
                padding = dip(12)
                orientation = LinearLayout.HORIZONTAL

                textView {
                    id = text_table_team_name
                    textSize = 12f
                    textColorResource = R.color.colorPrimary
                }.lparams(dip(75), wrapContent){
                    weight = 1f
                    marginEnd = dip(8)
                }

                textView {
                    id = text_table_played
                    textSize = 12f
                    gravity = Gravity.CENTER_HORIZONTAL
                }.lparams(wrapContent, wrapContent){weight = 1f}

                textView {
                    id = text_table_win
                    textSize = 12f
                    gravity = Gravity.CENTER_HORIZONTAL
                }.lparams(wrapContent, wrapContent){weight = 1f}

                textView {
                    id = text_table_draw
                    textSize = 12f
                    gravity = Gravity.CENTER_HORIZONTAL
                }.lparams(wrapContent, wrapContent){weight = 1f}

                textView {
                    id = text_table_loss
                    textSize = 12f
                    gravity = Gravity.CENTER_HORIZONTAL
                }.lparams(wrapContent, wrapContent){weight = 1f}

                textView {
                    id = text_table_total
                    textSize = 12f
                    gravity = Gravity.CENTER_HORIZONTAL
                }.lparams(wrapContent, wrapContent){weight = 1f}
            }
        }
    }
}

class TableViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val texTableTeamName: TextView = view.find(text_table_team_name)
    private val textTablePlayed: TextView = view.find(text_table_played)
    private val textTableWin: TextView = view.find(text_table_win)
    private val textTableDraw: TextView = view.find(text_table_draw)
    private val textTableLoss: TextView = view.find(text_table_loss)
    private val textTableTotal: TextView = view.find(text_table_total)

    fun bindItem(table: Table) {
        texTableTeamName.text = table.tableTeamName
        textTablePlayed.text = table.tablePlayed
        textTableWin.text = table.tableWin
        textTableDraw.text = table.tableDraw
        textTableLoss.text = table.tableLoss
        textTableTotal.text = table.tableTotal
    }
}