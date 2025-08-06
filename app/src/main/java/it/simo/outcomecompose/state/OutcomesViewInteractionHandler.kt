package it.simo.outcomecompose.state

import androidx.compose.runtime.staticCompositionLocalOf
import it.simo.outcomecompose.models.Outcome
import kotlinx.coroutines.flow.StateFlow

// TODO Is it possible to be just an interface to handle all the outcomes?
interface OutcomesViewInteractionHandler {
    fun onOutcomeEvent(event: OutcomeEvent)
    fun isOutcomeSelectedState(outcome: Outcome): StateFlow<Boolean>
}

sealed class OutcomeEvent{
    data class OnOutcomeClicked(val outcome: Outcome): OutcomeEvent()
}

val LocalOutcomesViewHandler = staticCompositionLocalOf<OutcomesViewInteractionHandler> {
     error("OutcomesViewInteractionHandler not provided!")
}