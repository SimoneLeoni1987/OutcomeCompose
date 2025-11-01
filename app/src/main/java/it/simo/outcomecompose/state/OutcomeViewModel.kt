package it.simo.outcomecompose.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.simo.outcomecompose.betslip.TempBetslipHelper
import it.simo.outcomecompose.domain.OutcomeLayoutType
import it.simo.outcomecompose.domain.getOutcomeLayoutType
import it.simo.outcomecompose.models.BetItem
import it.simo.outcomecompose.models.Game
import it.simo.outcomecompose.models.GameGroup
import it.simo.outcomecompose.models.Outcome
import it.simo.outcomecompose.models.SubGame
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val TAG = "OutcomeViewModel"

/**
 *
 * It manages the entier ui state for the screen that deals with the outcomes
 *
 */
class OutcomeViewModel : ViewModel(), IOutcomeViewModel {

    // It holds
    //  - the id of the selected subgame
    //  - the id of the selected outcome
    //  .. lets see if we need any other things

    // It also provides
    //  - the OutcomesScreenUiState

    private val _uiState = MutableStateFlow(OutcomeScreenUiState())
    override val uiState: StateFlow<OutcomeScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            listeningFromBetlispHelper()
        }
    }

    /**
     * Called when the user clicks on an outcome.
     */
    private fun oldOnOutcomeClicked(outcome: Outcome) {
        _uiState.update { state ->
            val newSelectedIds = if (outcome.getStableId() in state.selectedOutcomeIds) {
                state.selectedOutcomeIds - outcome.getStableId()
            } else {
                state.selectedOutcomeIds + outcome.getStableId()
            }
            state.copy(selectedOutcomeIds = newSelectedIds)
        }
    }

    override fun onOutcomeClicked(outcome: Outcome) {
        TempBetslipHelper.outcomeSelected(outcome)
    }

    private suspend fun listeningFromBetlispHelper() {
        TempBetslipHelper.outcomeSelectedFlow.collect {
            // TODO Create the other version, for the real betslip, where we have to interrogate
            //  every single outcome -> We need the entire outcome object

            _uiState.update { state ->
                val updatedSelectedOutcomeIds = mutableListOf<Int>()
                updatedSelectedOutcomeIds.addAll(TempBetslipHelper.selectedOutcomes)
                state.copy(selectedOutcomeIds = updatedSelectedOutcomeIds)
            }
        }
    }

    // The starting call to initialize the state
    fun initOutcomeViewModel(betItems: List<BetItem>) {
        // Synch with betslip manager
        val selected = initSelectedOutcomesFromBetslip(betItems)
        _uiState.update {
            it.copy(
                betItems = betItems,
                selectedOutcomeIds = selected
            )
        }
    }

    private fun initSelectedOutcomesFromBetslip(betItems: List<BetItem>): List<Int> {
        //  1. I have to search every outcome
        //  2. Is it selected in betslip?
        //    Y -> I need to insert it into the selected outcomes
        //    N -> Lets continue

        return betItems
            .flatMap { it.gameGroupList }
            .flatMap { it.gameList }
            .flatMap { it.subGameList }
            .flatMap { it.outcomeList }
            .filter { TempBetslipHelper.isOutcomeSelected(it) }
            .map { it.getStableId() }
            .toList()
    }


    fun onUpdateStateReviewed(uniquePath: UniquePath) {
        _uiState.update { state ->
            val updatedBetItems = state.betItems.map { betItem ->
                updateBetItemIfNeeded(betItem, uniquePath)
            }
            state.copy(betItems = updatedBetItems)
        }
    }

    private fun updateBetItemIfNeeded(betItem: BetItem, uniquePath: UniquePath): BetItem {
        val betItemUniquePath = uniquePath.betItem
        if (betItemUniquePath != null && betItem.getStableId() != betItemUniquePath.getStableId()) {
            return betItem
        }

        val updatedGameGroups = betItem.gameGroupList.map { gameGroup ->
            updateGameGroupIfNeeded(gameGroup, uniquePath)
        }
        return betItem.copy(gameGroupList = updatedGameGroups)
    }

    private fun updateGameGroupIfNeeded(gameGroup: GameGroup, uniquePath: UniquePath): GameGroup {
        val gameGroupUniquePath = uniquePath.gameGroup
        val layoutType = gameGroup.layout.getOutcomeLayoutType()

        // We are interested only in selection picker layout type
        // These are the possible layout that will handle the subgame selections
        // TODO We have to i_mprove, we need an action to know what to do .. so
        //  for example (SOGLIA action, where we have to set every picker with the some amount of
        //  subgame, so we will use that action information to substitute this "if"
        if (layoutType != OutcomeLayoutType.AdditionalInfoPicker) {
            return gameGroup
        }

        if (gameGroupUniquePath != null && gameGroup.getStableId() != gameGroupUniquePath.getStableId()) {
            return gameGroup
        }

        val updatedGames = gameGroup.gameList.map { game ->
            updateGameIfNeeded(game, uniquePath)
        }
        return gameGroup.copy(gameList = updatedGames)
    }

    private fun updateGameIfNeeded(game: Game, uniquePath: UniquePath): Game {
        val gameUniquePath = uniquePath.game
        if (gameUniquePath != null && game.getStableId() != gameUniquePath.getStableId()) {
            return game
        }

        val updatedSubGames = game.subGameList.map { subGameItem ->
            updateSubGameIfNeeded(subGameItem, uniquePath.subGame) // Pass only the relevant part
        }
        return game.copy(subGameList = updatedSubGames)
    }

    private fun updateSubGameIfNeeded(subGameItem: SubGame, subGameUniquePath: SubGame?): SubGame {
        // If we did pass a subgame, but it's not the one we want to modify
        if (subGameUniquePath != null && subGameItem.getStableId() != subGameUniquePath.getStableId()) {
            // If it's not the target subgame, and it's currently selected, deselect it.
            // Otherwise, keep its current state.
            // This addresses the implicit deselection logic if you only want ONE subgame selected.
            // If multiple subgames can be selected independently, this logic might need adjustment.
            return if (subGameItem.selected) subGameItem.copy(selected = false) else subGameItem
        }

        // If subGameUniquePath is null, it means we are not targeting a specific subgame to toggle.
        // In this case, we might want to deselect all subgames or keep them as is.
        // The original code implies only toggling if a subGameUniquePath is provided.
        if (subGameUniquePath == null) {
            // Option 1: Deselect if it was selected (if only one subgame can be selected overall)
            // return if (subGameItem.selected) subGameItem.copy(selected = false) else subGameItem

            // Option 2: Keep as is (if no specific subgame target, don't change anything)
            return subGameItem
        }

        // This is the target subgame, toggle its selection
        return subGameItem.copy(selected = !subGameItem.selected)
    }
}

// TODO Change name
// That's the class that we need to know the unique path of objects
data class UniquePath(
    val betItem: BetItem? = null,
    val gameGroup: GameGroup? = null,
    val game: Game? = null,
    val subGame: SubGame? = null,
    val outcome: Outcome? = null // -> Maybe not needed
)

// TODO This will be the action class to pass to the update state method, in order
//  to know which kind of operation we need to do
sealed class UpdateAction {
    data class SubGameSelectionAction(
        val selected: Boolean = true
    ) : UpdateAction()
}