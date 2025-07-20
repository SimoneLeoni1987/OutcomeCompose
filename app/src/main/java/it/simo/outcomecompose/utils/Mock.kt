package it.simo.outcomecompose.utils

import it.simo.outcomecompose.models.AdditionalInfo
import it.simo.outcomecompose.models.Outcome
import it.simo.outcomecompose.models.SubGame

// TODOLIST
//  [x] Create methods to create outcomes
//  [x] Create methods to create subgames
//  [x] Create methods to create additional info
//  [x] Create methods to create additional info
object Mock {
    fun createStaticSubgames(): List<SubGame> {
        val subgames = listOf(
            SubGame(
                subGameType = 1,
                subGameDescription = "Subgame 1",
                altSubGameDescription = "Alt Subgame 1",
                subGameCodeList = listOf(1, 2, 3),
                outcomeList = listOf(
                    Outcome(
                        outcomeCode = 1,
                        outcomeDescription = "Outcome 1",
                        altOutcomeDescription = "Alt Outcome 1",
                        outcomeOdds = 1,
                        selected = false
                    ),
                    Outcome(
                        outcomeCode = 2,
                        outcomeDescription = "Outcome 2",
                        altOutcomeDescription = "Alt Outcome 2",
                        outcomeOdds = 2,
                        selected = false
                    )
                )
            ),

            SubGame(
                subGameType = 1,
                subGameDescription = "Subgame 2",
                altSubGameDescription = "Alt Subgame 2",
                subGameCodeList = listOf(1, 2, 3),
                outcomeList = listOf(
                    Outcome(
                        outcomeCode = 1,
                        outcomeDescription = "Outcome 3",
                        altOutcomeDescription = "Alt Outcome 3",
                        outcomeOdds = 1,
                        selected = false
                    ),
                    Outcome(
                        outcomeCode = 2,
                        outcomeDescription = "Outcome 4",
                        altOutcomeDescription = "Alt Outcome 4",
                        outcomeOdds = 2,
                        selected = false
                    )
                )
            ),
            SubGame(
                subGameType = 1,
                subGameDescription = "Subgame 3",
                altSubGameDescription = "Alt Subgame 2",
                subGameCodeList = listOf(1, 2, 3),
                outcomeList = listOf(
                    Outcome(
                        outcomeCode = 1,
                        outcomeDescription = "Outcome 5",
                        altOutcomeDescription = "Alt Outcome 3",
                        outcomeOdds = 1,
                        selected = false
                    ),
                    Outcome(
                        outcomeCode = 2,
                        outcomeDescription = "Outcome 6",
                        altOutcomeDescription = "Alt Outcome 4",
                        outcomeOdds = 2,
                        selected = false
                    )
                )
            ),
            SubGame(
                subGameType = 1,
                subGameDescription = "Subgame 4",
                altSubGameDescription = "Alt Subgame 2",
                subGameCodeList = listOf(1, 2, 3),
                outcomeList = listOf(
                    Outcome(
                        outcomeCode = 1,
                        outcomeDescription = "Outcome 7",
                        altOutcomeDescription = "Alt Outcome 3",
                        outcomeOdds = 1,
                        selected = false
                    ),
                    Outcome(
                        outcomeCode = 2,
                        outcomeDescription = "Outcome 8",
                        altOutcomeDescription = "Alt Outcome 4",
                        outcomeOdds = 2,
                        selected = false
                    )
                )
            ),
            SubGame(
                subGameType = 1,
                subGameDescription = "Subgame 5",
                altSubGameDescription = "Alt Subgame 2",
                subGameCodeList = listOf(1, 2, 3),
                outcomeList = listOf(
                    Outcome(
                        outcomeCode = 1,
                        outcomeDescription = "Outcome 9",
                        altOutcomeDescription = "Alt Outcome 3",
                        outcomeOdds = 1,
                        selected = false
                    ),
                    Outcome(
                        outcomeCode = 2,
                        outcomeDescription = "Outcome 10",
                        altOutcomeDescription = "Alt Outcome 4",
                        outcomeOdds = 2,
                        selected = false
                    )
                )
            )
        )

        return subgames
    }

    fun createSubgames(numberOfSubgames: Int, numberOfSubGameCodeList: Int, numberOfOutcomes: Int): List<SubGame> {
        val subgames = mutableListOf<SubGame>()
        for (i in 0..numberOfSubgames) {
            subgames.add(createSubgame(i, numberOfSubGameCodeList, numberOfOutcomes))
        }
        return subgames
    }

    fun createSubgame(value: Int, numberOfSubGameCodeList: Int, numberOfOutcomes: Int): SubGame {

        val numberOfCodesAlreadyCreated = (value) * numberOfSubGameCodeList
        val startingCodeIndex = numberOfCodesAlreadyCreated

        val numberOfOutcomesAlreadyCreated = (value) * numberOfOutcomes
        val startingOutcomeIndex = numberOfOutcomesAlreadyCreated

        return SubGame(
            subGameType = 1,
            subGameDescription = "Subgame $value",
            altSubGameDescription = "Alt Subgame $value",
            subGameCodeList = createSubgameCodeList(startingValue = startingCodeIndex, numberOfSubGameCodeList),
            outcomeList = createOutcomeList(startingValue = startingOutcomeIndex, numberOfOutcomes)
        )
    }

    fun createAdditionalInfo(startingValue: Int, numberOfItems: Int): List<AdditionalInfo> {
        val additionalInfo = mutableListOf<AdditionalInfo>()
        for (i in startingValue..<startingValue + numberOfItems) {
            additionalInfo.add(createAdditionalInfoItem(i))
        }
        return additionalInfo
    }

    fun createAdditionalInfoItem(startingValue: Int): AdditionalInfo {
        return AdditionalInfo(
            additionalInfoDesc = "Additional Info $startingValue",
            additionalInfoValue = createAdditionalInfoValues(startingValue, numberOfValues = 3),
            reference = "Reference $startingValue"
        )
    }

    fun createAdditionalInfoValues(startingValue: Int, numberOfValues: Int): List<Int> {
        val values = mutableListOf<Int>()
        for (i in startingValue..<startingValue + numberOfValues) {
            values.add(i)
        }
        return values
    }

    fun createSubgameCodeList(startingValue: Int, numberOfCodes: Int): MutableList<Int> {
        val subgameCodeList = mutableListOf<Int>()
        for (i in startingValue..<startingValue + numberOfCodes) {
            subgameCodeList.add(i)
        }
        return subgameCodeList
    }

    fun createOutcomeList(startingValue: Int, numberOfOutcomes: Int): List<Outcome> {
        val outcomes = mutableListOf<Outcome>()
        for (i in startingValue..<startingValue + numberOfOutcomes) {
            outcomes.add(createOutcome(i))
        }
        return outcomes
    }

    fun createOutcome(value: Int): Outcome {
        return Outcome(
            outcomeCode = value,
            outcomeDescription = "Outcome $value",
            altOutcomeDescription = "Alt Outcome $value",
            outcomeOdds = value,
            selected = false
        )
    }
}