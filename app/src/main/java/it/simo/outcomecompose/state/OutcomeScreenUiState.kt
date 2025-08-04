package it.simo.outcomecompose.state

// Is it working maintaing the selected subgame id? Or is it too cumbersome
//  to maintain it here? Is it safer to handle inside the component?

// We are containing every outcomesview in a single screen
data class OutcomeScreenUiState(
    val selectedOutcomeIds: List<Int> = emptyList()
)