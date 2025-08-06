package it.simo.outcomecompose.ui.composables

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import it.simo.outcomecompose.localcompositions.LocalOutcomeViewModel
import it.simo.outcomecompose.models.Outcome
import it.simo.outcomecompose.models.SubGame
import it.simo.outcomecompose.state.OutcomeEvent
import it.simo.outcomecompose.ui.theme.Caption1_SemiBold
import it.simo.outcomecompose.ui.theme.OutcomeActive
import it.simo.outcomecompose.ui.theme.OutcomeDefault
import it.simo.outcomecompose.ui.theme.OutcomeHeight
import it.simo.outcomecompose.ui.theme.RoundCorner
import it.simo.outcomecompose.utils.Mock

const val TAG = "OutcomeButton"

// TODOLIST
//  [ ] Create linking between the betslip module and the outcome!

@Composable
fun OutcomeButton(
    modifier: Modifier = Modifier,
    outcome: Outcome,
    onClicked: () -> Unit
) {

    val viewModel = LocalOutcomeViewModel.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // We recompose only when the outcomes selected has changed
    val selected by remember(uiState.selectedOutcomeIds, outcome.getStableId()) {
        derivedStateOf { uiState.selectedOutcomeIds.contains(outcome.getStableId()) }
    }

    val backgroundColor = if (selected) {
        OutcomeActive
    } else {
        OutcomeDefault
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(RoundCorner))
            .background(color = backgroundColor)
            .clickable(onClick = { viewModel.onOutcomeClicked(outcome) } )
            .height(height = OutcomeHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            style = Caption1_SemiBold,
            text = outcome.outcomeDescription
        )
        Text(text = outcome.outcomeOdds.toString())
    }
}

@Preview
@Composable
fun OutcomeButtonPreview() {
    val outcome = Outcome(
        outcomeCode = 1,
        outcomeDescription = "Outcome 1",
        altOutcomeDescription = "Alt Outcome 1",
        outcomeOdds = 1,
        iconUrl = "",
        selected = false,
        subGame = Mock.createSubgame(1, 1, 1)
        )

    val context = LocalContext.current
    val modifier = Modifier.width(120.dp)

    OutcomeButton(outcome = outcome, onClicked = {
        Toast.makeText(context, "Toast", Toast.LENGTH_LONG).show()
    }, modifier = modifier)
}