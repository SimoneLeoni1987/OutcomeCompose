package it.simo.outcomecompose

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import it.simo.outcomecompose.data.DataGetter
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Data {

    @Test
    fun `get GameGroups returnin the right one`() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val gameGroups = DataGetter.getGameGroupList(appContext, "gamegroup/no_additional_info.json")
        TestCase.assertEquals(1, gameGroups.gameGroupList.size)
        TestCase.assertEquals("GameGroup", gameGroups.gameGroupList[0].type)
        TestCase.assertEquals(24, gameGroups.gameGroupList[0].betId)
        TestCase.assertEquals("", gameGroups.gameGroupList[0].betDescription)

        println("GameGroups = $gameGroups")
    }
}