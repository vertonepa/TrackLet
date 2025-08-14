package com.vertonepa.tracklet.tickets.presentation.ticket_list

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate

@Composable
fun TicketItem(
    title: String,
    date: LocalDate,
    deleteTicket: () -> Unit,
    navigateToDetails: () -> Unit,
) {
    ListItem(
        modifier = Modifier.clickable { navigateToDetails() },
        headlineContent = { Text(title) },
        supportingContent = { Text(date.toString()) },
        trailingContent = {
            Icon(
                Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier.clickable { deleteTicket() }
            )
        }
    )
}

@Preview
@Composable
private fun Preview() {
    TicketItem(
        title = "Title",
        date = LocalDate.now(),
        deleteTicket = {},
        navigateToDetails = {}
    )
}