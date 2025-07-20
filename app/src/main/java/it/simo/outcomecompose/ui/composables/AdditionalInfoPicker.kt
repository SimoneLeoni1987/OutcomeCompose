package it.simo.outcomecompose.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.simo.outcomecompose.R
import it.simo.outcomecompose.models.SubGame
import it.simo.outcomecompose.ui.theme.AdditionalInfoHeight
import it.simo.outcomecompose.ui.theme.AdditionalInfoItemHeight
import it.simo.outcomecompose.ui.theme.AdditionalInfoPickerBg
import it.simo.outcomecompose.ui.theme.AdditionalInfoPickerItemDefault
import it.simo.outcomecompose.ui.theme.AdditionalInfoPickerItemSelected
import it.simo.outcomecompose.ui.theme.Caption1_SemiBold
import it.simo.outcomecompose.ui.theme.Footnote_SemiBold
import it.simo.outcomecompose.ui.theme.RoundCorner
import it.simo.outcomecompose.ui.theme.Spacing2

/**
 *
 * This should be the picker element to show the subgames
 *
 */
@Composable
fun AdditionalInfoPicker(
    subgames: List<SubGame>,
    currentSelection: SubGame?, // is really useful?
    pageSize: Int = 3, // for the test sake
    onPickerClicked: (SubGame) -> Unit,
) {

    var selectedSubGame by remember {
        mutableStateOf(currentSelection ?: subgames.first())
    }

    var currentPage by remember {
        mutableIntStateOf(0)
    }

    val chunkedPages by remember(subgames, pageSize) {
        mutableStateOf(subgames.chunked(pageSize))
    }

    val subgamesToShow by remember(currentPage) {
        mutableStateOf(chunkedPages[currentPage])
    }

    // todo
    //  create the row with the subgames
    //  We have also other configuration for the row:
    //     - the page size (how many subgame per page)
    //  We have to implement also the arrows to change the page

    // doit
    //  I need to create the row with
    //  - the back arrow
    //  - the "page" with the page size number of subgame to visualize
    //  - the forward arrow
    //  Also I need to mantain the state of the current page

    fun getPreviousPage() {
        return if (currentPage > 0) {
            currentPage = currentPage - 1
        } else {
            currentPage = 0
        }
    }

    fun getNextPage() {
        return if (currentPage < chunkedPages.size - 1) {
            currentPage = currentPage + 1
        } else {
            currentPage = chunkedPages.size - 1
        }
    }

    // todo add the left and right arrow to do the pagination
    Row(
        modifier = Modifier
            .height(AdditionalInfoHeight)
            .fillMaxWidth()
            .clip(RoundedCornerShape(RoundCorner))
            .background(color = AdditionalInfoPickerBg)
            .padding(horizontal = Spacing2),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Todo
        //  I have to manage the pagination, at every click I need to give
        //  The following or the previous page


        // Lets create the pages
        // I have subgames and page size (how many subgame per page)


        AdditionalInfoPickerArrow(image = R.drawable.angle_left_small) {
            getPreviousPage()
        }

        for (i in 0 until subgamesToShow.size) {
            AdditionalInfoPickerItem(
                modifier = Modifier.weight(1f),
                subgame = subgamesToShow[i],
                selected = selectedSubGame.getStableId() == subgamesToShow[i].getStableId(),
                onClicked = {
                    selectedSubGame = subgamesToShow[i]
                    onPickerClicked(subgamesToShow[i])
                }
            )
        }

        AdditionalInfoPickerArrow(image = R.drawable.angle_right_small) {
            getNextPage()
        }

    }
}

@Composable
fun AdditionalInfoPickerArrow(
    image: Int = R.drawable.angle_left_small,
    onClicked: () -> Unit
) {

    Box(
        modifier = Modifier
            .height(AdditionalInfoItemHeight)
            .clip(RoundedCornerShape(RoundCorner))
            .clickable(onClick = onClicked)
    ) {
        Image(
            modifier = Modifier
                .width(32.dp)
                .align(Alignment.Center),
            painter = painterResource(id = image),
            contentDescription = "Arrow",
        )
    }
}

@Composable
fun AdditionalInfoPickerItem(
    modifier: Modifier = Modifier,
    subgame: SubGame,
    selected: Boolean = false,
    onClicked: () -> Unit
) {

    val backgroundColor = if (selected) {
        AdditionalInfoPickerItemSelected
    } else {
        AdditionalInfoPickerItemDefault
    }

    val textColor = if (selected) {
        Color.White
    } else {
        Color.Black
    }

    // passing the width from the outside
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(RoundCorner))
            .height(AdditionalInfoItemHeight)
            .background(color = backgroundColor)
            .clickable(onClick = onClicked),

        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = modifier.padding(horizontal = 10.dp),
            style = Footnote_SemiBold,
            color = textColor,
            text = subgame.subGameDescription
        )
    }
}

@Preview
@Composable
fun AdditionalInfoPickerPreview() {
    val subgames = listOf(
        SubGame(
            subGameDescription = "First Half",
            outcomeList = emptyList(),
            subGameCodeList = emptyList(),
            additionalInfo = emptyList(),
            subGameType = 1,
        ),
        SubGame(
            subGameDescription = "Match Result",
            outcomeList = emptyList(),
            subGameCodeList = emptyList(),
            additionalInfo = emptyList(),
            subGameType = 2
        ),
        SubGame(
            subGameDescription = "1X2",
            outcomeList = emptyList(),
            subGameCodeList = emptyList(),
            additionalInfo = emptyList(),
            subGameType = 3
        ),
        SubGame(
            subGameDescription = "U/O",
            outcomeList = emptyList(),
            subGameCodeList = emptyList(),
            additionalInfo = emptyList(),
            subGameType = 4
        )
    )

    Box(
        modifier = Modifier.padding(top = 40.dp)
    ) {
        AdditionalInfoPicker(
            subgames = subgames,
            pageSize = 2,
            currentSelection = subgames.first(),
            onPickerClicked = {}
        )
    }
}

@Preview
@Composable
fun AdditionalInfoPickerButtonPreview() {
    val sampleSubGame1 = SubGame(
        subGameDescription = "First Half",
        outcomeList = emptyList(),
        subGameCodeList = emptyList(),
        additionalInfo = emptyList(),
        subGameType = 1,
    )

    val sampleSubGame2 = SubGame(
        subGameDescription = "Match Result",
        outcomeList = emptyList(),
        subGameCodeList = emptyList(),
        additionalInfo = emptyList(),
        subGameType = 2
    )

    val sampleSubgame3 = SubGame(
        subGameDescription = "Second Half",
        outcomeList = emptyList(),
        subGameCodeList = emptyList(),
        additionalInfo = emptyList(),
        subGameType = 3
    )

    val sampleSubgame4 = SubGame(
        subGameDescription = "Third Half",
        outcomeList = emptyList(),
        subGameCodeList = emptyList(),
        additionalInfo = emptyList(),
        subGameType = 4
    )

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Adds space between buttons
    ) {
        Text("Button States:")

        // Unselected State
        AdditionalInfoPickerItem(
            subgame = sampleSubGame1,
            selected = false,
            onClicked = { /* Preview: Handle click for unselected */ }
        )

        // Selected State
        AdditionalInfoPickerItem(
            subgame = sampleSubGame2,
            selected = true,
            onClicked = { /* Preview: Handle click for selected */ }
        )

        // Another unselected to show variety
        AdditionalInfoPickerItem(
            subgame = sampleSubgame3,
            selected = false,
            onClicked = {}
        )

        // You can also add a stateful preview to test interaction
        Spacer(Modifier.height(16.dp))
        Text("Interactive Button:")
        var isInteractiveSelected by remember { mutableStateOf(false) }
        AdditionalInfoPickerItem(
            subgame = sampleSubgame4,
            selected = isInteractiveSelected,
            onClicked = { isInteractiveSelected = !isInteractiveSelected }
        )
    }
}