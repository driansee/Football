package com.drians.kade.football.view.standings

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.drians.kade.football.R
import com.drians.kade.football.adapter.TableAdapter
import com.drians.kade.football.api.ApiRepository
import com.drians.kade.football.model.Table
import com.drians.kade.football.util.invisible
import com.drians.kade.football.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TableFragment : Fragment(), AnkoComponent<Context>, TableView {

    private var table: MutableList<Table> = mutableListOf()
    private lateinit var presenter: TablePresenter
    private lateinit var adapter: TableAdapter
    private lateinit var listTable: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var id: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        id = activity?.intent?.getStringExtra("id").toString()

        adapter = TableAdapter(table)
        listTable.adapter = adapter

        presenter = TablePresenter(this, ApiRepository(), Gson())
        presenter.getTableList(id)
        swipeRefresh.onRefresh { presenter.getTableList(id) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            padding = dip(12)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    cardView {
                        lparams(matchParent, wrapContent) {
                            cardElevation = 8f
                            radius = 4f
                            useCompatPadding = true
                        }

                        linearLayout {
                            orientation = LinearLayout.VERTICAL

                            linearLayout {
                                padding = dip(12)
                                orientation = LinearLayout.HORIZONTAL

                                textView {
                                    textResource = R.string.text_table_team
                                    textSize = 12f
                                    textColor = Color.BLACK
                                }.lparams(dip(90), wrapContent){
                                    weight = 1f
                                    marginEnd = dip(8)
                                }

                                textView {
                                    textResource = R.string.text_table_played
                                    textSize = 12f
                                    textColor = Color.BLACK
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams(wrapContent, wrapContent){weight = 1f}

                                textView {
                                    textResource = R.string.text_table_win
                                    textSize = 12f
                                    textColor = Color.BLACK
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams(wrapContent, wrapContent){weight = 1f}

                                textView {
                                    textResource = R.string.text_table_draw
                                    textSize = 12f
                                    textColor = Color.BLACK
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams(wrapContent, wrapContent){weight = 1f}

                                textView {
                                    textResource = R.string.text_table_loss
                                    textSize = 12f
                                    textColor = Color.BLACK
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams(wrapContent, wrapContent){weight = 1f}

                                textView {
                                    textResource = R.string.text_table_total
                                    textSize = 12f
                                    textColor = Color.BLACK
                                    gravity = Gravity.CENTER_HORIZONTAL
                                }.lparams(wrapContent, wrapContent){weight = 1f}
                            }

                            view{
                                backgroundColorResource = android.R.color.darker_gray
                            }.lparams(matchParent, dip(1))

                            listTable = recyclerView {
                                lparams (width = matchParent, height = wrapContent)
                                layoutManager = LinearLayoutManager(context)
                            }
                        }
                    }
                    progressBar = progressBar {}.lparams{
                        topMargin = dip(50)
                        centerHorizontally()
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

    override fun showTableList(data: List<Table>) {
        swipeRefresh.isRefreshing = false
        table.clear()
        table.addAll(data)
        adapter.notifyDataSetChanged()
    }
}