package it.simo.outcomecompose

import it.simo.outcomecompose.models.AdditionalInfo
import it.simo.outcomecompose.models.Outcome
import it.simo.outcomecompose.models.SubGame
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class Models {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }



    @Test
    fun `getStableId should return consistent hash for identical SubGames`() {
        val outcome1 = Outcome(1, "Outcome 1", "Alt Outcome 1", 100, null, false)
        val outcome2 = Outcome(2, "Outcome 2", "Alt Outcome 2", 200, null, false)

        val additionalInfo1 = AdditionalInfo("Info A", listOf(1, 2))
        val additionalInfo2 = AdditionalInfo("Info B", listOf(1, 2))
        val additionalInfo3 = AdditionalInfo("Info B", listOf(1, 2))

        val subGame1 = SubGame(
            subGameDescription = "First Half",
            outcomeList = listOf(outcome1, outcome2),
            subGameType = 1,
            subGameCodeList = listOf(101, 102),
            additionalInfo = listOf(additionalInfo1, additionalInfo2)
        )
        val subGame2 = SubGame(
            subGameDescription = "First Half",
            outcomeList = listOf(outcome1, outcome2), // Same outcomes
            subGameType = 1,
            subGameCodeList = listOf(101, 102),
            additionalInfo = listOf(additionalInfo1, additionalInfo3) // Same additional info
        )

        Assert.assertEquals(subGame1.getStableId(), subGame2.getStableId())
    }
}