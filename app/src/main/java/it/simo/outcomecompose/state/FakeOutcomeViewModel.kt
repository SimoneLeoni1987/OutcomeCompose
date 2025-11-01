package it.simo.outcomecompose.state

import it.simo.outcomecompose.models.Outcome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeOutcomeViewModel : IOutcomeViewModel {
    override val uiState: StateFlow<OutcomeScreenUiState> =
        MutableStateFlow(OutcomeScreenUiState())

    override fun onOutcomeClicked(outcome: Outcome) {
    }
}
    