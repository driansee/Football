package com.drians.kade.football.model

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("idTeam") var teamId: String? = null,
    @SerializedName("strTeam") var teamName: String? = null,
    @SerializedName("strTeamBadge") var teamBadge: String? = null,
    @SerializedName("strDescriptionEN") var teamDescriptionEn: String? = null,
    @SerializedName("intFormedYear") var teamFormedYear: String? = null,
    @SerializedName("strStadium") var teamStadium: String? = null,
    @SerializedName("strStadiumThumb") var teamStadiumThumb: String? = null,
    @SerializedName("strStadiumLocation") var teamStadiumLocation: String? = null,
    @SerializedName("intStadiumCapacity") var teamStadiumCapacity: String? = null,
    @SerializedName("strStadiumDescription") var teamStadiumDescription: String? = null
)

