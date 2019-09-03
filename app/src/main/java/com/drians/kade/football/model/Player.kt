package com.drians.kade.football.model

import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("idPlayer") var playerId: String? = null,
    @SerializedName("strPlayer") var playerName: String? = null,
    @SerializedName("strCutout") var playerCutout: String? = null,
    @SerializedName("strDescriptionEN") var playerDescriptionEn: String? = null,
    @SerializedName("strBirthLocation") var playerBirthLocation: String? = null,
    @SerializedName("dateBorn") var playerDateBorn: String? = null,
    @SerializedName("strHeight") var playerHeight: String? = null,
    @SerializedName("strWeight") var playerWeight: String? = null,
    @SerializedName("strGender") var playerGender: String? = null,
    @SerializedName("strNationality") var playerNationality: String? = null,
    @SerializedName("strTeam") var playerTeam: String? = null,
    @SerializedName("strTeamNational") var playerTeamNational: String? = null,
    @SerializedName("strSide") var playerSide: String? = null,
    @SerializedName("strPosition") var playerPosition: String? = null,
    @SerializedName("strNumber") var playerNumber: String? = null,
    @SerializedName("dateSigned") var playerDateSigned: String? = null,
    @SerializedName("strSigning") var playerSigning: String? = null,
    @SerializedName("strKit") var playerKit: String? = null,
    @SerializedName("strWage") var playerWage: String? = null,
    @SerializedName("strFanart1") var playerFanart1: String? = null
)