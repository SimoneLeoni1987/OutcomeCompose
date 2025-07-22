package it.simo.outcomecompose

import android.content.Context
import androidx.lifecycle.ViewModel
import it.simo.outcomecompose.data.DataGetter
import it.simo.outcomecompose.data.response.GameGroupsResponse
import it.simo.outcomecompose.models.GameGroup
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainViewUiState())
    val uiState: StateFlow<MainViewUiState> = _uiState.asStateFlow()

    fun getData(context: Context) {
        val response = DataGetter.getGameGroupList(context, "mixed_gamegroups/mixed.json")
        _uiState.update { it.copy(gameGroups = response.gameGroupList) }
    }
}

data class MainViewUiState(
    // just for test
    val gameGroups: List<GameGroup> = emptyList(),
)