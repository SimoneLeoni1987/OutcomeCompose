package it.simo.outcomecompose.betslip

import android.util.Log
import androidx.compose.foundation.layout.add
import it.simo.outcomecompose.models.Outcome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlin.collections.remove

const val TAG = "TempBetslipHelper"

// This wil be an external module
object TempBetslipHelper {

    // Temp
    val selectedOutcomes = mutableListOf(
        -1237686852,
        530444510,
        -117605957,
    )

    private var _applicationScope: CoroutineScope? = null

    val outcomeSelectedFlow: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun init(applicationScope: CoroutineScope) {
        _applicationScope = applicationScope

        outcomeSelectedFlow.shareIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.WhileSubscribed(5000),
            replay = 0
        )
    }

    // This will be called at the start of the section where the outcomes are
    fun isOutcomeSelected(outcome: Outcome): Boolean {
        return outcome.getStableId() in selectedOutcomes
    }

    private fun notifyOutcomeEvent() {
        _applicationScope?.launch {
            outcomeSelectedFlow.emit(Unit)
        }
    }

    fun outcomeSelected(outcome: Outcome) {
        val stableId = outcome.getStableId()
        if (isOutcomeSelected(outcome)) {
            selectedOutcomes.remove(stableId)
            Log.d(TAG, "Outcome deselected: ${outcome.outcomeDescription}, ID: $stableId")
        } else {
            selectedOutcomes.add(stableId)
            Log.d(TAG, "Outcome selected: ${outcome.outcomeDescription}, ID: $stableId")
        }

        notifyOutcomeEvent()
    }
}