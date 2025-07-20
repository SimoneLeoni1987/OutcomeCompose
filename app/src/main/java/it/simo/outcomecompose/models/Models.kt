package it.simo.outcomecompose.models

// TODOLIST
//  [ ] Create interface to get the stableId method
//  [ ] Consider using by lazy to calculate only once the hash

data class Event(
    val programCode: Int,
    val sportCode: Int,
    val leagueCode: Int,
    val eventCode: Int,
    val leagueDescription: String,
    val leagueImageUrl: String,
    val eventDescription: String,
    val eventDate: Long,
    val teamHome: Team,
    val timeLive: String,
    val teamAway: Team,
    val sportDescription: String,
    val sportBackgroundImageUrl: String,
    val gamesNumber: Int,
    val live: Boolean
)

data class BetItem(
    val event: Event,
    val player: Player,
    val gameGroupList: List<GameGroup>,
    val isScorecast: Boolean
)

data class GameGroup(
    val type: String,
    val betId: Int,
    val betDescription: String,
    val layoutType: Int,
    val layout: Layout,
    val gameList: List<Game>
)

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
    val player: Player
)

data class SubGame(
    val subGameDescription: String,
    val altSubGameDescription: String = "",
    val outcomeList: List<Outcome>,
    val subGameType: Int,
    val subGameCodeList: List<Int>,
    val additionalInfo: List<AdditionalInfo>? = null,
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
        result = 31 * result + (additionalInfo?.hashCode() ?: 0)
        result = 31 * result + subGameType.hashCode()
        result = 31 * result + outcomeList.hashCode()
        return result
    }
}

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

data class Player(
    val playerId: String,
    val isNull: Boolean = false,
    val playerName: String,
    val team: Team,
    val playerPosition: String,
    val teams: List<Team>
)

data class Team(
    val teamId: Int = -1,
    val teamImageUrl: String,
    val description: String,
    val score: String,
    val players: List<Player>
)

data class Layout(
    val layoutType: String,
    val additionalInfo: String,
    val rows: Int,
    val columns: Int,
    val pickerPaging: Boolean,
    val multiPicker: Boolean,
    val pickerPages: Int
)