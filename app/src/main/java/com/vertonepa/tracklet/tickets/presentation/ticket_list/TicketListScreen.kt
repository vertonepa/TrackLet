package com.vertonepa.tracklet.tickets.presentation.ticket_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vertonepa.tracklet.tickets.domain.model.TicketListModel
import java.time.LocalDate

@Composable
fun TicketListRoute(
    viewModel: TicketListViewModel = hiltViewModel(),
    navigateToDetails: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TicketListScreen(
        uiState = uiState,
        onDeleteTicket = { viewModel.onDeleteTicket(it) },
        navigateToDetails = { navigateToDetails(it) }
    )
}

@Composable
private fun TicketListScreen(
    uiState: TicketListUIState,
    onDeleteTicket: (String) -> Unit,
    navigateToDetails: (String) -> Unit
) {
    when (uiState) {
        TicketListUIState.EmptyList -> EmptyListScreen()
        TicketListUIState.Loading -> LoadingScreen()
        is TicketListUIState.Success -> {
            LazyColumn {
                items(items = uiState.tickets) {
                    TicketItem(
                        title = it.ticketHeading,
                        date = it.ticketPublishDate,
                        deleteTicket = { onDeleteTicket(it.ticketId) }
                    ) {
                        navigateToDetails(it.ticketId)
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LinearProgressIndicator()
    }

}

@Composable
private fun EmptyListScreen() {
    Box(
        modifier = Modifier
            .background(Color.Companion.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "No hay tickets por el momento", fontSize = 24.sp)
    }
}

@Preview
@Composable
private fun Success_Preview() {
    TicketListScreen(
        uiState = TicketListUIState.Success(
            listOf(
                TicketListModel(ticketId = "a", "Título 1", LocalDate.now()),
                TicketListModel(ticketId = "b", "Título 2", LocalDate.now()),
                TicketListModel(ticketId = "c", "Título 3", LocalDate.now()),
                TicketListModel(ticketId = "d", "Título 4", LocalDate.now()),
            )
        ),
        onDeleteTicket = {},
        navigateToDetails = {},
    )
}

@Preview
@Composable
private fun Empty_Preview() {
    EmptyListScreen()
}
