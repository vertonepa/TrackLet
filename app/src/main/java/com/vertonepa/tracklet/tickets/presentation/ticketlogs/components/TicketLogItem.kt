package com.vertonepa.tracklet.tickets.presentation.ticketlogs.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vertonepa.tracklet.core.ui.TrackletIcons
import com.vertonepa.tracklet.tickets.domain.model.enums.PaymentState
import com.vertonepa.tracklet.tickets.presentation.ticketlogs.UiTotals
import java.time.LocalDate

@Composable
fun TicketLogItem(
    date: LocalDate,
    isSelected: Boolean,
    productColor: String,
    productQuantity: Int,
    hasMark: Boolean,
    paymentState: String,
    modifier: Modifier = Modifier,
    shouldShowBottomSheet: () -> Unit,
    haptic: () -> Unit,
    onClickCard: () -> Unit,
    onLongClickCard: () -> Unit
) {
    val interaction = remember { MutableInteractionSource() }

    //TODO("Cuando hasMark se active, el item pasará a ser PAID")
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .combinedClickable(
                interactionSource = interaction,
                indication = LocalIndication.current,
                onLongClick = {
                    haptic()
                    onLongClickCard()
                },
                onClick = onClickCard
            ),
        border = if (isSelected) {
            BorderStroke(2.dp, Color(0xFFF8FCA3))
        } else {
            null
        },
        elevation = if (isSelected) CardDefaults.elevatedCardElevation() else CardDefaults.cardElevation()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = modifier
                    .background(Color.Magenta)
                    .padding(2.dp),
                text = paymentState
            )
            if (isSelected) {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = shouldShowBottomSheet) {
                    Icon(
                        painter = painterResource(TrackletIcons.KebabMenu),
                        contentDescription = null
                    )
                }
            }
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                ItemText(text = "Fecha")
                ItemText(text = "$date", fontWeight = FontWeight.Bold)
            }
            Column(modifier = Modifier.weight(1f)) {
                ItemText(text = "Color")
                ItemText(text = productColor, fontWeight = FontWeight.Bold)
            }
            Column(modifier = Modifier.weight(1f)) {
                ItemText(text = "Cantidad")
                ItemText(text = "$productQuantity", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun ColumnScope.ItemText(
    text: String,
    fontWeight: FontWeight? = null,
    textAlign: Alignment.Horizontal = Alignment.CenterHorizontally,
) {
    Text(
        modifier = Modifier
            .align(textAlign)
            .padding(vertical = 4.dp),
        text = text,
        fontWeight = fontWeight,
        maxLines = 1
    )
}

@Composable
fun ProductTotalsCard(
    totals: UiTotals,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        ItemText(text = "Cantidad Total")
        ItemText(text = "${totals.quantity}", fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
private fun TicketLogItem_Preview() {
    TicketLogItem(
        date = LocalDate.now(),
        isSelected = true,
        productColor = "Rojo",
        productQuantity = 12,
        paymentState = PaymentState.OWES.state,
        hasMark = false,
        shouldShowBottomSheet = {},
        onClickCard = {},
        onLongClickCard = {},
        haptic = {}
    )


}

@Preview
@Composable
private fun TotalsCard_Preview() {
    ProductTotalsCard(
        totals = UiTotals(
            quantity = 300,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    )
}