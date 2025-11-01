package it.simo.outcomecompose.localcompositions

import androidx.compose.runtime.staticCompositionLocalOf
import it.simo.outcomecompose.state.IOutcomeViewModel
import it.simo.outcomecompose.state.OutcomeViewModel

val LocalOutcomeViewModel = staticCompositionLocalOf<IOutcomeViewModel> {
    error("OutcomeViewModel not provided!")
}