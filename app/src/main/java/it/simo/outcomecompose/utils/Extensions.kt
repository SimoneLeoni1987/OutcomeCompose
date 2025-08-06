package it.simo.outcomecompose.utils

import it.simo.outcomecompose.models.BetItem

fun List<BetItem>.processAndLinkParsedData(): List<BetItem> {
    for (betItem in this) {
        for (gameGroup in betItem.gameGroupList) {
            for (game in gameGroup.gameList) {
                for (subGame in game.subGameList) {
                    for (outcome in subGame.outcomeList) {
                        outcome.betItem = betItem
                        outcome.subGame = subGame
                    }
                }
            }
        }
    }
    return this
}