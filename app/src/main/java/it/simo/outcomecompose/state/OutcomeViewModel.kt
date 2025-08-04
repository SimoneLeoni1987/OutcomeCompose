package it.simo.outcomecompose.state

import androidx.compose.animation.core.copy
import androidx.lifecycle.ViewModel
import it.simo.outcomecompose.models.Outcome
import it.simo.outcomecompose.models.SubGame
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 *
 * It manages the entier ui state for the screen that deals with the outcomes
 *
 */
class OutcomeViewModel : ViewModel(), OutcomesViewInteractionHandler {

    // It holds
    //  - the id of the selected subgame
    //  - the id of the selected outcome
    //  .. lets see if we need any other things

    // It also provides
    //  - the OutcomesScreenUiState

    private val _uiState = MutableStateFlow(OutcomeScreenUiState())
    val currentUiState: StateFlow<OutcomeScreenUiState> = _uiState.asStateFlow()

    /**
     * Called when the user clicks on an outcome.
     */
    private fun onOutcomeClicked(outcome: Outcome) {
        _uiState.update { state ->
            val newSelectedIds = if (outcome.getStableId() in state.selectedOutcomeIds) {
                state.selectedOutcomeIds - outcome.getStableId()
            } else {
                state.selectedOutcomeIds + outcome.getStableId()
            }
            state.copy(selectedOutcomeIds = newSelectedIds)
        }
    }

    override fun onOutcomeEvent(event: OutcomeEvent) {
        when (event) {
            is OutcomeEvent.OnOutcomeClicked -> {
                onOutcomeClicked(event.outcome)
            }
        }
    }

    override fun isOutcomeSelectedState(outcome: Outcome): StateFlow<Boolean> {
        TODO("Not yet implemented")
    }
}