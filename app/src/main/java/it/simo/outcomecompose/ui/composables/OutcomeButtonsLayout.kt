package it.simo.outcomecompose.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    var maxValue = outcomes.size

    Column(
        verticalArrangement = Arrangement.spacedBy(OutcomesVerticalSpacing)
    ) {
        while (buttonsInserted < maxValue) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(OutcomesHorizontalSpacing),
            ) {
                for (i in 0 until min(maxValue - buttonsInserted, columns)) {
                    val index = buttonsInserted + i
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