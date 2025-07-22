package it.simo.outcomecompose.domain

import it.simo.outcomecompose.models.Game
import it.simo.outcomecompose.models.Outcome
import it.simo.outcomecompose.models.SubGame

object Converters {

    /**
     *
     * Get all the outcomes from the each games, taking the first subgame
     *
     */
    fun List<Game>.toOutcomes(): List<Outcome> {
        // we have to get all the outcomes for each game, taking the first subgame
        val outcomes = mutableListOf<Outcome>()

        this.forEach { game ->
            game.subGameList.firstOrNull()?.let { subGame ->
                subGame.outcomeList.forEach { outcome ->
                    outcomes.add(outcome)
                }
            }

        }
        return outcomes
    }

    /**
     *
     * Get all the subgames from the first game
     *
     */
    fun List<Game>.toSubgames(): List<SubGame> {
        val subgames = mutableListOf<SubGame>()
        val firstGame = this.firstOrNull()

        firstGame?.subGameList?.forEach { subGame ->
            subgames.add(subGame)
        }

        return subgames
    }
}