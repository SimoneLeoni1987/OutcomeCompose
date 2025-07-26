package it.simo.outcomecompose.ui.composables

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.simo.outcomecompose.domain.Converters.toOutcomes
import it.simo.outcomecompose.domain.Converters.toSubgames
import it.simo.outcomecompose.domain.OutcomeLayoutType
import it.simo.outcomecompose.domain.getOutcomeLayoutType
import it.simo.outcomecompose.models.BetItem
import it.simo.outcomecompose.ui.theme.CardBg
import it.simo.outcomecompose.ui.theme.PurpleGrey40
import it.simo.outcomecompose.ui.theme.Spacing3
import it.simo.outcomecompose.ui.theme.Spacing4
import it.simo.outcomecompose.utils.Mock


@Composable
fun CardLayout(
    modifier: Modifier = Modifier,
    betItem: BetItem
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
                gameGroups = betItem.gameGroupList
            )

        }
    }
}

@Preview
@Composable
fun CardLayoutPreview() {

    val modifier = Modifier
        .size(width = 240.dp, height = 100.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = PurpleGrey40),
        contentAlignment = Alignment.Center
    ) {
        CardLayout(
            modifier = modifier,
            betItem = Mock.createBetItem()
        )
    }
}