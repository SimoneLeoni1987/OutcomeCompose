package it.simo.outcomecompose.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import it.simo.outcomecompose.models.Outcome
import it.simo.outcomecompose.ui.theme.OutcomesHorizontalSpacing
import it.simo.outcomecompose.ui.theme.OutcomesVerticalSpacing
import kotlin.math.min

@Composable
fun OutcomeButtonsLayout(
    outcomes: List<Outcome>,
    columns: Int,
    onOutcomeClicked: (Outcome) -> Unit
) {
    var buttonsInserted = 0
    var outcomesCount = outcomes.size

    Column(
        verticalArrangement = Arrangement.spacedBy(OutcomesVerticalSpacing)
    ) {
        while (buttonsInserted < outcomesCount) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(OutcomesHorizontalSpacing),
            ) {
                (0 until min(outcomesCount - buttonsInserted, columns)).forEach { i ->
                    val index = buttonsInserted
                    OutcomeButton(
                        outcome = outcomes[index],
                        onClicked = { onOutcomeClicked(outcomes[index]) },
                        modifier = Modifier.weight(1f)
                    )
                    buttonsInserted++
                }
            }
        }
    }
}

@Preview
@Composable
fun OutcomeButtonsLayoutPreview() {

    val outcomes = listOf(
        Outcome(1, "1", "", 250, null, false),
        Outcome(2, "2", "", 250, null, false),
        Outcome(3, "3", "", 250, null, false),
        Outcome(4, "4", "", 250, null, false),
        Outcome(5, "5", "", 250, null, false),
    )

    OutcomeButtonsLayout(
        outcomes = outcomes,
        columns = 3,
        onOutcomeClicked = {}
    )

}