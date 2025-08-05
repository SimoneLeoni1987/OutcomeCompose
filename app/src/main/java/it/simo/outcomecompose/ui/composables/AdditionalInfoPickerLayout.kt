package it.simo.outcomecompose.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.simo.outcomecompose.models.SubGame
import it.simo.outcomecompose.state.UniquePath
import it.simo.outcomecompose.ui.theme.Spacing3
import it.simo.outcomecompose.utils.Mock

/**
 *
 * This should be the picker element to show the subgames
 *
 */

@Composable
fun AdditionalInfoPickerLayout(
    allSubGames: List<SubGame>,
    columns: Int = 3,
    pageSize: Int,
    onUserSelectedSubGame: (SubGame) -> Unit,
    uniquePathSelected: (UniquePath) -> Unit
) {

    var localSelectedSubGame by remember(allSubGames) {
        mutableStateOf(allSubGames.firstOrNull {subGame -> subGame.selected} ?: allSubGames.first())
    }

    val outcomesForSelectedSubGame = remember(localSelectedSubGame) {
        localSelectedSubGame.outcomeList
    }

    // Maybe lazy column?
    Column {
        AdditionalInfoPicker(
            subgames = allSubGames,
            pageSize = pageSize,
            currentSelection = localSelectedSubGame,
            onPickerClicked = { newSubGame ->
                onUserSelectedSubGame(newSubGame)
                uniquePathSelected(UniquePath(
                    subGame = newSubGame,
                ))
            }
        )

        Spacer(modifier = Modifier.padding(bottom = Spacing3))

        OutcomeButtonsLayout(
            outcomes = outcomesForSelectedSubGame, columns = columns, onOutcomeClicked = {}
        )
    }
}

@Preview
@Composable
fun AdditionalInfoPickerLayoutPreview() {
    val subgames = Mock.createSubgames(numberOfSubgames = 5, numberOfSubGameCodeList = 3, numberOfOutcomes = 3)

    Box(modifier = Modifier.padding(top = 40.dp)
    ) {
        AdditionalInfoPickerLayout(
            allSubGames = subgames,
            columns = 2,
            pageSize = 2,
            onUserSelectedSubGame = {},
            uniquePathSelected = {}
        )
    }
}