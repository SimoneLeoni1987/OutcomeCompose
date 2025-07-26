package it.simo.outcomecompose

import android.content.Context
import androidx.lifecycle.ViewModel
import it.simo.outcomecompose.data.DataGetter
import it.simo.outcomecompose.models.BetItem
import it.simo.outcomecompose.models.GameGroup
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
        val response = DataGetter.getBetItemList(context, "betitems/mixed.json")
        _uiState.update { it.copy(betItems = response.betItems) }
    }
}

data class MainViewUiState(
    val gameGroups: List<GameGroup> = emptyList(),
    val betItems: List<BetItem> = emptyList()
)