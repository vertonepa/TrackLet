package com.vertonepa.tracklet.navigation.graphs.details_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vertonepa.tracklet.tickets.presentation.details.TicketDetailsRoute
import com.vertonepa.tracklet.tickets.presentation.edit.EditTicketRoute
import com.vertonepa.tracklet.tickets.presentation.ticketlogs.TicketLogsRoute
import kotlinx.serialization.Serializable

@Serializable
data class DetailsDestination(val id: Int)

@Serializable
data class EditTicketDestination(val id: Int)

@Serializable
data class TicketLogsDestination(val id: Int)

fun NavGraphBuilder.detailsScreen(
    navigateToEditing: (Int) -> Unit,
    navigateToTicketLogs: (Int) -> Unit,
    navigateUp: () -> Unit
) {
    composable<DetailsDestination> {
        TicketDetailsRoute(
            navigateUp = navigateUp,
            navigateToEditing = navigateToEditing,
            navigateToTicketLogs = navigateToTicketLogs,
        )
    }
    composable<EditTicketDestination> {
        EditTicketRoute(
            navigateUp = navigateUp
        )
    }
    composable<TicketLogsDestination> {
        TicketLogsRoute(
            navigateUp = navigateUp
        )
    }
}

fun NavController.navigateToDetailsScreen(id: Int) {
    navigate(DetailsDestination(id))
}

fun NavController.navigateToEditingScreen(id: Int) {
    navigate(EditTicketDestination(id))
}

fun NavController.navigateToTicketLogsScreen(id: Int) {
    navigate(TicketLogsDestination(id))
}