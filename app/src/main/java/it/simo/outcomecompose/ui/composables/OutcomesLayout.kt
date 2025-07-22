package it.simo.outcomecompose.ui.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import it.simo.outcomecompose.domain.Converters.toOutcomes
import it.simo.outcomecompose.domain.Converters.toSubgames
import it.simo.outcomecompose.domain.OutcomeLayoutType
import it.simo.outcomecompose.domain.getOutcomeLayoutType
import it.simo.outcomecompose.models.GameGroup

// Todo
//  implementing the logic from the gamegroup list
//  outcomes handled:
//  -> Classic outcomes
//  -> Additional info picker

@Composable
fun OutcomesLayout(
    gameGroups: List<GameGroup>
) {
    // todo
    //  -> for each game group, it will be generating
    //      an OutcomeLayoutType
    //  ->  depending on the type, it will generate a layout composable

    // Lets use a lazy column

    LazyColumn {
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
    }
}
