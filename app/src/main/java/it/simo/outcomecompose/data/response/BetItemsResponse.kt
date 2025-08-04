package it.simo.outcomecompose.data.response

import androidx.annotation.Keep
import it.simo.outcomecompose.models.BetItem

@Keep
data class BetItemsResponse(
    val betItems: List<BetItem>
)