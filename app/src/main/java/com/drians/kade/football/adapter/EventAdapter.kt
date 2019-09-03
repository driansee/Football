package com.drians.kade.football.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import com.drians.kade.football.R
import com.drians.kade.football.R.id.*
import com.drians.kade.football.util.changeFormatDate
import com.drians.kade.football.util.strTodate
import com.drians.kade.football.util.toGMTFormat
import com.drians.kade.football.model.Event
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import java.text.SimpleDateFormat

class EventAdapter (private val events: List<Event>, private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<EventViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(EventUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

    override fun getItemCount(): Int = events.size
}

class EventUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {

        cardView {
            lparams(matchParent, wrapContent) {
                margin = dip(8)
                cardElevation = 8f
                radius = 4f
            }

            linearLayout {
                orientation = LinearLayout.VERTICAL
                padding = dip(8)
                lparams(matchParent, wrapContent)

                textView {
                    id = text_event_name
                    textColorResource = android.R.color.black
                    textSize = 16f
                    gravity = Gravity.CENTER
                }.lparams(matchParent, wrapContent){ topMargin = dip(12)}

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER
                    lparams(matchParent, wrapContent){topMargin = dip(8)}

                    textView {
                        id = text_event_date
                        textColorResource = R.color.colorPrimaryDark
                    }.lparams(wrapContent, wrapContent){
                        marginEnd = dip(8)
                    }

                    textView {
                        id = text_event_time
                        textColorResource = R.color.colorPrimary
                    }.lparams(wrapContent, wrapContent)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    lparams(matchParent, wrapContent) { bottomMargin = dip(8) }

                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER_HORIZONTAL
                        lparams(dip(0), wrapContent) {
                            gravity = Gravity.CENTER
                            weight = 3f
                        }

                        textView {
                            id = text_event_home_team
                            gravity = Gravity.CENTER
                        }.lparams(wrapContent, wrapContent){ topMargin = dip(25) }
                    }

                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER_HORIZONTAL
                        lparams(dip(0), wrapContent) {
                            gravity = Gravity.CENTER
                            weight = 1f
                        }

                        textView {
                            id = text_event_home_score
                            textSize = 28f
                            textResource = android.R.color.black
                            typeface = Typeface.DEFAULT_BOLD
                        }.lparams(wrapContent, wrapContent){ topMargin = dip(8) }
                    }

                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER_HORIZONTAL
                        lparams(dip(0), wrapContent){
                            gravity = Gravity.CENTER
                            weight = 1f
                        }

                        textView {
                            textResource = R.string.text_vs
                            textSize = 18f
                        }.lparams(wrapContent, wrapContent){ topMargin = dip(8)}
                    }

                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER_HORIZONTAL
                        lparams(dip(0), wrapContent){
                            gravity = Gravity.CENTER
                            weight = 1f
                        }

                        textView {
                            id = text_event_away_score
                            textSize = 28f
                            textResource = android.R.color.black
                            typeface = Typeface.DEFAULT_BOLD
                        }.lparams(wrapContent, wrapContent){ topMargin = dip(8)}
                    }

                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER
                        lparams(dip(0), wrapContent){ weight = 3f}

                        textView {
                            id = text_event_away_team
                            gravity = Gravity.CENTER
                        }.lparams(wrapContent, wrapContent){ topMargin = dip(25)}
                    }
                }

            }
        }
    }
}

class EventViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val textEventName: TextView = view.find(text_event_name)
    private val textEventDate: TextView = view.find(text_event_date)
    private val textEventTime: TextView = view.find(text_event_time)
    private val textHomeTeam: TextView = view.find(text_event_home_team)
    private val textHomeScore: TextView = view.find(text_event_home_score)
    private val textAwayTeam: TextView = view.find(text_event_away_team)
    private val textAwayScore: TextView = view.find(text_event_away_score)

    @SuppressLint("SimpleDateFormat")
    fun bindItem(events: Event, listener: (Event) -> Unit) {

        textEventName.text = events.eventName
        val date = strTodate(events.eventDate)
        val dateTime = toGMTFormat(events.eventDate, events.eventTime)
        textEventDate.text = changeFormatDate(date)
        textEventTime.text = SimpleDateFormat("HH:mm").format(dateTime)
        textHomeTeam.text = events.eventHomeTeam
        textHomeScore.text = events.eventHomeScore
        textAwayTeam.text = events.eventAwayTeam
        textAwayScore.text = events.eventAwayScore
        itemView.setOnClickListener { listener(events) }
    }
}