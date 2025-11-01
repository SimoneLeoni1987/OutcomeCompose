package it.simo.outcomecompose.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.simo.outcomecompose.localcompositions.LocalOutcomeViewModel
import it.simo.outcomecompose.models.BetItem
import it.simo.outcomecompose.models.SubGame
import it.simo.outcomecompose.state.FakeOutcomeViewModel
import it.simo.outcomecompose.state.UniquePath
import it.simo.outcomecompose.ui.theme.CardBg
import it.simo.outcomecompose.ui.theme.PurpleGrey40
import it.simo.outcomecompose.ui.theme.Spacing3
import it.simo.outcomecompose.utils.Mock


@Composable
fun CardLayout(
    modifier: Modifier = Modifier,
    betItem: BetItem,
    onSubGameSelected: (SubGame) -> Unit,
    onPathSelected: (UniquePath) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = CardBg,
        )
    ) {
        Column(
            modifier = Modifier
                .padding(Spacing3)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = betItem.event.eventDescription)
            Spacer(modifier = Modifier.size(Spacing3))
            OutcomesLayout(
                gameGroups = betItem.gameGroupList,
                onSubGameSelected = onSubGameSelected,
                uniquePathSelected = { uniquePath ->
                    onPathSelected(uniquePath.copy(
                        betItem = betItem
                    ))
                }
            )
        }
    }
}

@Preview
@Composable
fun CardLayoutPreview() {

    val modifier = Modifier
        .size(width = 240.dp, height = 100.dp)

    CompositionLocalProvider(LocalOutcomeViewModel provides FakeOutcomeViewModel()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = PurpleGrey40),
            contentAlignment = Alignment.Center
        ) {
            CardLayout(
                modifier = modifier,
                betItem = Mock.createBetItem(),
                onSubGameSelected = {},
                onPathSelected = {}
            )
        }
    }
}