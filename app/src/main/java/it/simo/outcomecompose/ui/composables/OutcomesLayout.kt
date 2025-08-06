package it.simo.outcomecompose.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import it.simo.outcomecompose.domain.Converters.toClassicOutcomes
import it.simo.outcomecompose.domain.Converters.toSubgames
import it.simo.outcomecompose.domain.OutcomeLayoutType
import it.simo.outcomecompose.domain.getOutcomeLayoutType
import it.simo.outcomecompose.models.GameGroup
import it.simo.outcomecompose.models.SubGame
import it.simo.outcomecompose.state.UniquePath
import it.simo.outcomecompose.ui.theme.Spacing3

// Todo
//  implementing the logic from the gamegroup list
//  outcomes handled:
//  -> Classic outcomes
//  -> Additional info picker

@Composable
fun OutcomesLayout(
    gameGroups: List<GameGroup>,
    onSubGameSelected: (SubGame) -> Unit,
    uniquePathSelected: (UniquePath) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing3)
    ) {
        for (gameGroup in gameGroups) {
            val layoutType = gameGroup.layout.getOutcomeLayoutType()
            if (layoutType != null) {
                when (layoutType) {
                    is OutcomeLayoutType.Classic -> {
                        OutcomeButtonsLayout(gameGroup.gameList.toClassicOutcomes(),
                            columns = gameGroup.layout.columns,
                            onOutcomeClicked = {})
                    }

                    is OutcomeLayoutType.AdditionalInfoPicker -> {
                        val game = gameGroup.gameList.first()
                        AdditionalInfoPickerLayout(
                            allSubGames = game.toSubgames(),
                            pageSize = gameGroup.layout.pickerPages,
                            columns = gameGroup.layout.columns,
                            onUserSelectedSubGame = onSubGameSelected,
                            uniquePathSelected = { uniquePath ->
                                uniquePathSelected(uniquePath.copy(
                                    game = game,
                                    gameGroup = gameGroup
                                ))
                            }
                        )
                    }
                }
            }
        }
    }
}
