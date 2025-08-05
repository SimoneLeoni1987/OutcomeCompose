package it.simo.outcomecompose

import android.content.Context
import androidx.lifecycle.ViewModel
import it.simo.outcomecompose.data.DataGetter
import it.simo.outcomecompose.domain.OutcomeLayoutType
import it.simo.outcomecompose.domain.getOutcomeLayoutType
import it.simo.outcomecompose.models.BetItem
import it.simo.outcomecompose.models.GameGroup
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
}

data class MainViewUiState(
    val gameGroups: List<GameGroup> = emptyList(),
    val betItems: List<BetItem> = emptyList()
)