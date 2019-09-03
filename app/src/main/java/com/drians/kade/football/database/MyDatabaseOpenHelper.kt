package com.drians.kade.football.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.drians.kade.football.model.FavoriteMatch
import com.drians.kade.football.model.FavoriteTeam
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteMatch.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // create table favorite match
        db.createTable(
            FavoriteMatch.TABLE_FAVORITE_MATCH, true,
            FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteMatch.EVENT_ID to TEXT + UNIQUE,
            FavoriteMatch.EVENT_NAME to TEXT,
            FavoriteMatch.EVENT_DATE to TEXT,
            FavoriteMatch.EVENT_TIME to TEXT,
            FavoriteMatch.EVENT_HOME_TEAM_ID to TEXT,
            FavoriteMatch.EVENT_HOME_TEAM to TEXT,
            FavoriteMatch.EVENT_HOME_SCORE to TEXT,
            FavoriteMatch.EVENT_AWAY_TEAM_ID to TEXT,
            FavoriteMatch.EVENT_AWAY_TEAM to TEXT,
            FavoriteMatch.EVENT_AWAY_SCORE to TEXT)

        // create table favorite team
        db.createTable(
            FavoriteTeam.TABLE_FAVORITE_TEAM, true,
            FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.TEAM_BADGE to TEXT,
            FavoriteTeam.TEAM_DESCRIPTION_EN to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(FavoriteMatch.TABLE_FAVORITE_MATCH, true)
        db.dropTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true)
    }
}

    // Access property for Context
    val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)