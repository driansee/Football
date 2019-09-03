package com.drians.kade.football.view.main

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.Toast
import com.drians.kade.football.R
import com.drians.kade.football.R.id.*
import com.drians.kade.football.view.favorite.FavoriteFragment
import com.drians.kade.football.view.info.AboutFragment
import com.drians.kade.football.view.league.LeagueFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var mBottomNavigationView: BottomNavigationView
    private var doubleBackToExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // MainActivity UI
        linearLayout {
            orientation = LinearLayout.VERTICAL

            frameLayout {
                id = main_container
            }.lparams(matchParent, dip(0)) {
                weight = 1f
            }

            mBottomNavigationView = bottomNavigationView {
                id = bottom_navigation
                backgroundColor = Color.WHITE
                inflateMenu(R.menu.bottom_nav_menu)
            }.lparams(matchParent, wrapContent){
                gravity = Gravity.BOTTOM
            }
        }


        supportActionBar?.setTitle(R.string.title_bar_league)

        val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                navigation_league -> {
                    supportActionBar?.setTitle(R.string.title_bar_league)
                    loadLeagueFragment(savedInstanceState)
                    return@OnNavigationItemSelectedListener true
                }
                navigation_favorite -> {
                    supportActionBar?.setTitle(R.string.title_bar_favorite)
                    loadFavoriteFragment(savedInstanceState)
                    return@OnNavigationItemSelectedListener true
                }
                navigation_about -> {
                    supportActionBar?.setTitle(R.string.title_bar_about)
                    loadAboutFragment(savedInstanceState)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
        mBottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        mBottomNavigationView.selectedItemId = navigation_league
    }

    private fun loadLeagueFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(main_container, LeagueFragment(), LeagueFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavoriteFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(main_container, FavoriteFragment(), FavoriteFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadAboutFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(main_container, AboutFragment(), AboutFragment::class.java.simpleName)
                .commit()
        }
    }

    override fun onBackPressed() {
        if(doubleBackToExit) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExit = true
        toast(R.string.double_back_to_exit)
        Handler().postDelayed({ doubleBackToExit =  false }, 2000)
    }
}