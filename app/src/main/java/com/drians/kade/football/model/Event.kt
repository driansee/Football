package com.drians.kade.football.model

import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("idLeague") var leagueId: String? = null,
    @SerializedName("idEvent") var eventId: String? = null,
    @SerializedName("strEvent") var eventName: String? = null,
    @SerializedName("dateEvent") var eventDate: String? = null,
    @SerializedName("strTime") var eventTime: String? = null,
    @SerializedName("idHomeTeam") var homeTeamId: String? = null,
    @SerializedName("strHomeTeam") var eventHomeTeam: String? = null,
    @SerializedName("intHomeScore") var eventHomeScore: String? = null,
    @SerializedName("idAwayTeam") var awayTeamId: String? = null,
    @SerializedName("strAwayTeam") var eventAwayTeam: String? = null,
    @SerializedName("intAwayScore") var eventAwayScore: String? = null,

    // home team
    @SerializedName("strHomeGoalDetails") var eventHomeGoalDetails: String? = null,
    @SerializedName("strHomeRedCards") var eventHomeRedCards: String? = null,
    @SerializedName("strHomeYellowCards") var eventHomeYellowCards: String? = null,
    @SerializedName("strHomeLineupGoalkeeper") var eventHomeLineupGoalkeeper: String? = null,
    @SerializedName("strHomeLineupDefense") var eventHomeLineupDefense: String? = null,
    @SerializedName("strHomeLineupMidfield") var eventHomeLineupMidfield: String? = null,
    @SerializedName("strHomeLineupForward") var eventHomeLineupForward: String? = null,
    @SerializedName("strHomeLineupSubstitutes") var eventHomeLineupSubstitutes: String? = null,
    @SerializedName("intHomeShots") var eventHomeShots: String? = null,

    // away team
    @SerializedName("strAwayGoalDetails") var eventAwayGoalDetails: String? = null,
    @SerializedName("strAwayRedCards") var eventAwayRedCards: String? = null,
    @SerializedName("strAwayYellowCards") var eventAwayYellowCards: String? = null,
    @SerializedName("strAwayLineupGoalkeeper") var eventAwayLineupGoalkeeper: String? = null,
    @SerializedName("strAwayLineupDefense") var eventAwayLineupDefense: String? = null,
    @SerializedName("strAwayLineupMidfield") var eventAwayLineupMidfield: String? = null,
    @SerializedName("strAwayLineupForward") var eventAwayLineupForward: String? = null,
    @SerializedName("strAwayLineupSubstitutes") var eventAwayLineupSubstitutes: String? = null,
    @SerializedName("intAwayShots") var eventAwayShots: String? = null
)