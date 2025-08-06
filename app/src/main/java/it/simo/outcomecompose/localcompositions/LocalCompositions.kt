package it.simo.outcomecompose.localcompositions

import androidx.compose.runtime.staticCompositionLocalOf
import it.simo.outcomecompose.state.OutcomeViewModel

val LocalOutcomeViewModel = staticCompositionLocalOf<OutcomeViewModel> {
    error("OutcomeViewModel not provided!")
}