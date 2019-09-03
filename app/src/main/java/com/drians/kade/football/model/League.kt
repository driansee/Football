package com.drians.kade.football.model

import com.google.gson.annotations.SerializedName

data class League(
    @SerializedName("idLeague") var leagueId: String? = null,
    var mLeagueName: String? = null,
    var mLeagueBadge: Int? = null,
    @SerializedName("strLeague") var leagueName: String? = null,
    @SerializedName("strSport") var leagueSport: String? = null,
    @SerializedName("intFormedYear") var leagueFormedYear: String? = null,
    @SerializedName("strCountry") var leagueCountry: String? = null,
    @SerializedName("strBadge") var leagueBadge: String? = null,
    @SerializedName("strDescriptionEN") var leagueDescriptionEN: String? = null
)