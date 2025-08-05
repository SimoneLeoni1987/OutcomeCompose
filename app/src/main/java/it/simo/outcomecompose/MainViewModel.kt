package it.simo.outcomecompose

import android.content.Context
import androidx.compose.animation.core.copy
import androidx.compose.ui.semantics.selected
import androidx.lifecycle.ViewModel
import it.simo.outcomecompose.data.DataGetter
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

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainViewUiState())
    val uiState: StateFlow<MainViewUiState> = _uiState.asStateFlow()

    fun getGameGroups(context: Context) {
        val response = DataGetter.getGameGroupList(context, "mixed_gamegroups/mixed.json")
        _uiState.update { it.copy(gameGroups = response.gameGroupList) }
    }

    fun getBetItems(context: Context) {
        val response = DataGetter.getBetItemList(context, "betitems/reduced_mixed.json")
        _uiState.update { it.copy(betItems = response.betItems) }
    }

    fun onSubGameSelected(subGame: SubGame) {
        // I have to update the selected subgame in the ui state
        _uiState.update { state ->
            val updatedBetItems = state.betItems.map { betItem ->
                val updatedGameGroups = betItem.gameGroupList.map { gameGroup ->
                    val layoutType = gameGroup.layout.getOutcomeLayoutType()

                    // We are interested only in selection picker layout type
                    if (layoutType != OutcomeLayoutType.AdditionalInfoPicker) {
                        return@map gameGroup
                    }

                    val updatedGames = gameGroup.gameList.map { game ->
                        val updatedSubGames = game.subGameList.map { subGameItem ->
                            if (subGameItem.getStableId() == subGame.getStableId()) {
                                subGameItem.copy(selected = !subGameItem.selected)
                            } else {
                                subGameItem.copy(selected = false)
                            }
                        }
                        game.copy(subGameList = updatedSubGames)
                    }
                    gameGroup.copy(gameList = updatedGames)
                }
                betItem.copy(gameGroupList = updatedGameGroups)
            }
            state.copy(betItems = updatedBetItems)
        }
    }

    // TODO
    //  also we need to pass a list of possible actions on the unique path
    fun onUpdateState(uniquePath: UniquePath) {
        val betItemUniquePath = uniquePath.betItem
        val gameGroupUniquePath = uniquePath.gameGroup
        val gameUniquePath = uniquePath.game
        val subGameUniquePath = uniquePath.subGame

        // I have to update the selected subgame in the ui state
        _uiState.update { state ->
            val updatedBetItems = state.betItems.map { betItem ->

                // If the betItemUniquePath is null, we have to return the betItem
                if (betItemUniquePath != null && betItem.getStableId() != betItemUniquePath.getStableId()) {
                    return@map betItem
                }

                val updatedGameGroups = betItem.gameGroupList.map { gameGroup ->
                    val layoutType = gameGroup.layout.getOutcomeLayoutType()

                    // We are interested only in selection picker layout type
                    // These are the possible layout that will handle the subgame selections
                    if (layoutType != OutcomeLayoutType.AdditionalInfoPicker) {
                        return@map gameGroup
                    }

                    if (gameGroupUniquePath != null && gameGroup.getStableId() != gameGroupUniquePath.getStableId()) {
                        return@map gameGroup
                    }

                    val updatedGames = gameGroup.gameList.map { game ->

                        if (gameUniquePath != null && game.getStableId() != gameUniquePath.getStableId()) {
                            return@map game
                        }

                        val updatedSubGames = game.subGameList.map { subGameItem ->

                            // If we did pass a subgame, but its not the one we want
                            if (subGameUniquePath != null && subGameItem.getStableId() != subGameUniquePath.getStableId()) {
                                return@map subGameItem
                            }

                            // TODO We have to introduce the logic for the possible actions..
                            //  for the moment we only handle the subgame selection
                            if (subGameItem.getStableId() != subGameUniquePath?.getStableId()) {
                                subGameItem.copy(selected = false)
                            } else {
                                subGameItem.copy(selected = !subGameItem.selected)
                            }
                        }
                        game.copy(subGameList = updatedSubGames)
                    }
                    gameGroup.copy(gameList = updatedGames)
                }
                betItem.copy(gameGroupList = updatedGameGroups)
            }
            state.copy(betItems = updatedBetItems)
        }
    }


    fun onUpdateStateReviewed(uniquePath: UniquePath) {
        val betItemUniquePath = uniquePath.betItem
        val gameGroupUniquePath = uniquePath.gameGroup
        val gameUniquePath = uniquePath.game
        val subGameUniquePath = uniquePath.subGame

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
        // TODO We have to improve, we need an action to know what to do .. so
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

data class MainViewUiState(
    val gameGroups: List<GameGroup> = emptyList(),
    val betItems: List<BetItem> = emptyList()
)