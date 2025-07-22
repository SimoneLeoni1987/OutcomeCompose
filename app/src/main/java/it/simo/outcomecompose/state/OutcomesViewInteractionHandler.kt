package it.simo.outcomecompose.state

import androidx.compose.runtime.staticCompositionLocalOf
import it.simo.outcomecompose.models.Outcome
import kotlinx.coroutines.flow.StateFlow

interface OutcomesViewInteractionHandler {
    fun onOutcomeEvent(event: OutcomeEvent)
    fun isOutcomeSelectedState(outcome: Outcome): StateFlow<Boolean>
}

sealed class OutcomeEvent{
    data class OnOutcomeClicked(val outcome: Outcome): OutcomeEvent()
}

val LocalOutcomesViewHandler = staticCompositionLocalOf<OutcomesViewInteractionHandler?> {
    // doit try to understand this

    // Default value provider.
    // Providing null forces consumers to handle the case where the handler might not be provided,
    // which can help catch setup errors.
    // Alternatively, you could provide a no-op implementation or throw an error:
    // { error("OutcomesViewInteractionHandler not provided!") }
    null
}