package it.simo.outcomecompose.state

import it.simo.outcomecompose.models.Outcome
import kotlinx.coroutines.flow.StateFlow

interface IOutcomeViewModel {
    val uiState: StateFlow<OutcomeScreenUiState>
    fun onOutcomeClicked(outcome: Outcome)
}