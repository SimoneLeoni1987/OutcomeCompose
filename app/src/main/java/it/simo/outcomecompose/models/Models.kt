package it.simo.outcomecompose.models

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import it.simo.outcomecompose.domain.Converters.toSubgames

// TODOLIST
//  [ ] Create interface to get the stableId method
//  [ ] Consider using by lazy to calculate only once the hash
//  [ ] Using also the event data in the outcome to calculate the stable id


@Keep
data class Event(
    val programCode: Int,
    val sportCode: Int,
    val leagueCode: Int,
    val eventCode: Int,
    val leagueDescription: String?,
    val leagueImageUrl: String?,
    val eventDescription: String,
    val eventDate: Long,
    val teamHome: Team?,
    val timeLive: String?,
    val teamAway: Team?,
    val sportDescription: String?,
    val sportBackgroundImageUrl: String?,
    val gamesNumber: Int,
    val live: Boolean
) {
    fun getStableId(): Int {
        var result = eventCode.hashCode()
        result = 31 * result + leagueCode.hashCode()
        result = 31 * result + programCode.hashCode()
        result = 31 * result + sportCode.hashCode()

        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Event
        if (eventCode != other.eventCode) return false
        if (leagueCode != other.leagueCode) return false
        if (programCode != other.programCode) return false
        if (sportCode != other.sportCode) return false
        if (teamHome != other.teamHome) return false
        if (teamAway != other.teamAway) return false
        if (timeLive != other.timeLive) return false

        return true
    }

    override fun hashCode(): Int {
        var result = eventCode.hashCode()
        result = 31 * result + leagueCode.hashCode()
        result = 31 * result + programCode.hashCode()
        result = 31 * result + sportCode.hashCode()
        result = 31 * result + teamHome.hashCode()
        result = 31 * result + teamAway.hashCode()
        result = 31 * result + timeLive.hashCode()
        return result
    }
}

@Keep
data class BetItem(
    val event: Event,
    val player: Player?,
    val gameGroupList: List<GameGroup>,
    val isScorecast: Boolean
) {
    fun getStableId(): Int {
        var result = event.eventCode.hashCode()
        result = 31 * result + player.hashCode()
        result = 31 * result + isScorecast.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as BetItem
        if (event.eventDescription != other.event.eventDescription) return false
        if (event.eventCode != other.event.eventCode) return false
        if (player != other.player) return false
        if (gameGroupList != other.gameGroupList) return false
        if (isScorecast != other.isScorecast) return false

        return true
    }

    override fun hashCode(): Int {
        var result = event.eventCode.hashCode()
        result = 31 * result + event.eventDescription.hashCode()
        result = 31 * result + player.hashCode()
        result = 31 * result + gameGroupList.hashCode()
        result = 31 * result + isScorecast.hashCode()
        return result
    }

}

@Keep
data class GameGroup(
    val type: String,
    val betId: Int,
    val betDescription: String,
    val layoutType: Int,
    val layout: Layout,
    val gameList: List<Game>
) {

    fun getStableId(): Int {
        var result = betDescription.hashCode()
        result = 31 * result + betId.hashCode()

        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as GameGroup
        if (betId != other.betId) return false
        if (betDescription != other.betDescription) return false
        if (gameList != other.gameList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = betDescription.hashCode()
        result = 31 * result + betId.hashCode()
        result = 31 * result + gameList.hashCode()
        return result
    }

}

@Keep
data class Game(
    val type: String,
    val gameCode: Int,
    val gameDescription: String,
    val outcomeListCode: Int,
    val cashable: Boolean,
    val comboMin: Int,
    val comboMax: Int,
    val comboAams: Int,
    val live: Boolean,
    val subGameList: List<SubGame>,
    val priority: Int,
    val statusId: Int,
    val layoutType: Int,
    val player: Player?
) {

    fun getStableId(): Int {
        var result = gameDescription.hashCode()
        result = 31 * result + gameCode.hashCode()
        result = 31 * result + outcomeListCode.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Game
        if (gameCode != other.gameCode) return false
        if (gameDescription != other.gameDescription) return false
        if (outcomeListCode != other.outcomeListCode) return false
        if (subGameList != other.subGameList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = gameDescription.hashCode()
        result = 31 * result + gameCode.hashCode()
        result = 31 * result + outcomeListCode.hashCode()
        result = 31 * result + subGameList.hashCode()
        return result
    }
}

@Keep
data class SubGame(
    val subGameDescription: String,
    val altSubGameDescription: String? = "",
    val outcomeList: List<Outcome>,
    val subGameType: Int,
    val subGameCodeList: List<Int>,
    val additionalInfo: List<AdditionalInfo>? = null,

    // Business
    val selected: Boolean = false
) {
    fun getStableId(): Int {
        var result = subGameDescription.hashCode()
        subGameCodeList.forEach { code ->
            result = 31 * result + (code.hashCode())
        }

        additionalInfo?.forEach { infoItem ->
            result = 31 * result + infoItem.getStableId()
        }

        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SubGame

        if (selected != other.selected) return false
        if (subGameDescription != other.subGameDescription) return false
        if (subGameCodeList != other.subGameCodeList) return false
        if (additionalInfo != other.additionalInfo) return false
        if (subGameType != other.subGameType) return false
        if (outcomeList != other.outcomeList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = subGameDescription.hashCode()
        result = 31 * result + subGameCodeList.hashCode()
        result = 31 * result + selected.hashCode()
        result = 31 * result + (additionalInfo?.hashCode() ?: 0)
        result = 31 * result + subGameType.hashCode()
        result = 31 * result + outcomeList.hashCode()
        return result
    }
}

// TODO
//  Add something from the event to make it unique
@Keep
data class Outcome(
    val outcomeCode: Int,
    var outcomeDescription: String,
    val altOutcomeDescription: String,
    val outcomeOdds: Int,
    val iconUrl: String? = null,
    val selected: Boolean
) {
    fun getStableId(): Int {
        var hash = outcomeDescription.hashCode()
        hash = 31 * hash + (outcomeCode.hashCode())
        return hash
    }

    // Lets exclude for the moment the selected ..
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Outcome

        if (outcomeCode != other.outcomeCode) return false
        if (outcomeDescription != other.outcomeDescription) return false
        if (altOutcomeDescription != other.altOutcomeDescription) return false
        if (outcomeOdds != other.outcomeOdds) return false

        return true
    }

    override fun hashCode(): Int {
        var hash = outcomeDescription.hashCode()
        hash = 31 * hash + (outcomeCode.hashCode())
        hash = 31 * hash + altOutcomeDescription.hashCode()
        hash = 31 * hash + outcomeOdds.hashCode()
        return hash
    }
}

@Keep
data class AdditionalInfo(
    val additionalInfoDesc: String,
    val additionalInfoValue: List<Int>,
    val reference: String = ""
) {
    fun getStableId(): Int {
        var result = additionalInfoDesc.hashCode()
        additionalInfoValue.forEach {
            result = 31 * result + it.hashCode()
        }
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AdditionalInfo

        if (additionalInfoDesc != other.additionalInfoDesc) return false
        if (additionalInfoValue != other.additionalInfoValue) return false

        return true
    }

    override fun hashCode(): Int {
        var hash = additionalInfoDesc.hashCode()
        hash = 31 * hash + additionalInfoValue.hashCode()
        return hash
    }
}

@Keep
data class Player(
    val playerId: String,
    val isNull: Boolean = false,
    val playerName: String,
    val team: Team,
    val playerPosition: String,
    val teams: List<Team>
)

@Keep
data class Team(
    val teamId: Int = -1,
    val teamImageUrl: String,
    val description: String,
    val score: String,
    val players: List<Player>
) {

    fun getStableId(): Int {
        var result = teamId.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Team
        if (teamId != other.teamId) return false
        if (description != other.description) return false
        if (score != other.score) return false

        return true
    }

    override fun hashCode(): Int {
        var result = teamId.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + score.hashCode()
        return result
    }
}

@Keep
data class Layout(
    val layoutType: String,
    val additionalInfo: String?,
    val rows: Int,
    val columns: Int,
    val pickerPaging: Boolean,
    val multiPicker: Boolean,
    val pickerPages: Int
)