package com.drians.kade.football.view.info

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.LinearLayout
import com.drians.kade.football.R
import org.jetbrains.anko.*


class AboutFragment : Fragment(), AnkoComponent<Context> {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {

        relativeLayout {
            lparams(matchParent, wrapContent)

            linearLayout {
                orientation = LinearLayout.VERTICAL
                padding = dip(12)
                gravity = Gravity.CENTER
                lparams(matchParent, matchParent)

                imageView {
                    setImageResource(R.drawable.img_about)
                    setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY)
                }.lparams(dip(96), dip(96))

                textView {
                    textResource = R.string.text_aplication_name
                    textSize = 16f
                    textColor = Color.BLACK
                    gravity = Gravity.CENTER
                }.lparams(matchParent, wrapContent) { topMargin= dip(12) }

                textView {
                    textResource = R.string.text_about_author
                    textSize = 14f
                    gravity = Gravity.CENTER
                }.lparams(matchParent, wrapContent) { topMargin= dip(4) }

                textView {
                    textResource = R.string.text_about_copyright
                    textSize = 12f
                    gravity = Gravity.CENTER
                }.lparams(matchParent, wrapContent) { topMargin = dip(4) }
            }
        }
    }
}