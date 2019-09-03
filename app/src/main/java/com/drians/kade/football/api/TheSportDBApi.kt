package com.drians.kade.football.api

import com.drians.kade.football.BuildConfig

object TheSportDBApi {

    /**
     * Detail liga
     * https://www.thesportsdb.com/api/v1/json/1/lookupleague.php?id={idLeague}
     */
    fun getLeagueDetail(idLeague: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupleague.php?id=" + idLeague
    }

    /**
     * Detail pertandingan
     * https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id={idEvent}
     */
    fun getEventDetail(idEvent: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupevent.php?id=" + idEvent
    }

    /**
     * Daftar next match
     * https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id={idLeague}
     */
    fun getEventNext(idLeague: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventsnextleague.php?id=" + idLeague
    }

    /**
     * Daftar previous match
     * https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id={idLeague}
     */
    fun getEventPrevious(idLeague: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php?id=" + idLeague
    }

    /**
     * Klasemen pertandingan
     * https://www.thesportsdb.com/api/v1/json/1/lookuptable.php?l={idLeague}
     */
    fun getTableList(idLeague: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookuptable.php?l=" + idLeague
    }

    /**
     * Daftar tim
     * https://www.thesportsdb.com/api/v1/json/1/lookup_all_teams.php?id={idLeague}
     */
    fun getTeamList(idLeague: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookup_all_teams.php?id=" + idLeague
    }

    /**
     * Detail tim
     * https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id={idTeam}
     */
    fun getTeamDetail(idTeam: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + idTeam
    }

    /**
     * Daftar pemain
     * https://www.thesportsdb.com/api/v1/json/1/lookup_all_players.php?id={idTeam}
     */
    fun getPlayerList(idTeam: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookup_all_players.php?id=" + idTeam
    }

    /**
     * Detail pemain
     * https://www.thesportsdb.com/api/v1/json/1/lookupplayer.php?id={idPemain}
     */
    fun getPlayerDetail(idPlayer: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupplayer.php?id=" + idPlayer
    }

    /**
     * Pencarian pertandingan
     * https://www.thesportsdb.com/api/v1/json/1/searchevents.php?e={query}
     */
    fun getEventSearch(strEvent: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/searchevents.php?e=" + strEvent
    }

    /**
     * Pencarian tim
     * https://www.thesportsdb.com/api/v1/json/1/searchteams.php?t={query}
     */
    fun getTeamSearch(strTeam: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/searchteams.php?t=" + strTeam
    }

}