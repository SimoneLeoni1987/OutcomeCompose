package it.simo.outcomecompose.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import it.simo.outcomecompose.domain.Converters.toOutcomes
import it.simo.outcomecompose.domain.Converters.toSubgames
import it.simo.outcomecompose.domain.OutcomeLayoutType
import it.simo.outcomecompose.domain.getOutcomeLayoutType
import it.simo.outcomecompose.models.GameGroup
import it.simo.outcomecompose.models.SubGame
import it.simo.outcomecompose.ui.theme.OutcomeHeight
import it.simo.outcomecompose.ui.theme.Spacing3
import it.simo.outcomecompose.utils.Mock

// Todo
//  implementing the logic from the gamegroup list
//  outcomes handled:
//  -> Classic outcomes
//  -> Additional info picker

@Composable
fun OutcomesLayout(
    gameGroups: List<GameGroup>,
    onSubGameSelected: (SubGame) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing3)
    ) {
        for (gameGroup in gameGroups) {
            val layoutType = gameGroup.layout.getOutcomeLayoutType()
            if (layoutType != null) {
                when (layoutType) {
                    is OutcomeLayoutType.Classic -> {
                        OutcomeButtonsLayout(gameGroup.gameList.toOutcomes(),
                            columns = gameGroup.layout.columns,
                            onOutcomeClicked = {})
                    }

                    is OutcomeLayoutType.AdditionalInfoPicker -> {
                        AdditionalInfoPickerLayout(
                            gameGroup.gameList.toSubgames(),
                            pageSize = gameGroup.layout.pickerPages,
                            columns = gameGroup.layout.columns,
                            onUserSelectedSubGame = onSubGameSelected
                        )
                    }
                }
            }
        }
    }

    /*LazyColumn(
        userScrollEnabled = false
    ) {
        items(gameGroups.size) { index ->
            val gameGroup = gameGroups[index]

            val layoutType = gameGroup.layout.getOutcomeLayoutType()
            if (layoutType != null) {
                when (layoutType) {
                    is OutcomeLayoutType.Classic -> {
                        OutcomeButtonsLayout(gameGroup.gameList.toOutcomes(),
                            columns = gameGroup.layout.columns,
                            onOutcomeClicked = {})
                    }

                    is OutcomeLayoutType.AdditionalInfoPicker -> {
                        AdditionalInfoPickerLayout(
                            gameGroup.gameList.toSubgames(),
                            pageSize = gameGroup.layout.pickerPages,
                            columns = gameGroup.layout.columns,
                            onUserSelectedSubGame = {}
                        )
                    }
                }
            }
        }
    }*/
}
