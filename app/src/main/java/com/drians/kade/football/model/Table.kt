package com.drians.kade.football.model

import com.google.gson.annotations.SerializedName

data class Table(
    @SerializedName("teamid") var tableTeamId: String? = null,
    @SerializedName("name") var tableTeamName: String? = null,
    @SerializedName("played") var tablePlayed: String? = null,
    @SerializedName("win") var tableWin: String? = null,
    @SerializedName("draw") var tableDraw: String? = null,
    @SerializedName("loss") var tableLoss: String? = null,
    @SerializedName("total") var tableTotal: String? = null
)