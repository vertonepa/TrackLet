package com.vertonepa.tracklet.navigation.graphs.details_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vertonepa.tracklet.tickets.presentation.details.TicketDetailsRoute
import com.vertonepa.tracklet.tickets.presentation.editing.TicketEditingRoute
import com.vertonepa.tracklet.tickets.presentation.ticketlogs.TicketLogsRoute
import kotlinx.serialization.Serializable

@Serializable
data class Details(val id: String)

@Serializable
data class Editing(val id: String)

@Serializable
data class TicketLogs(val id: String)

fun NavGraphBuilder.detailsScreen(
    navigateToEditing: (String) -> Unit,
    navigateToTicketLogs: (String) -> Unit,
    navigateUp: () -> Unit
) {
    composable<Details> {
        TicketDetailsRoute(
            navigateUp = navigateUp,
            navigateToEditing = navigateToEditing,
            navigateToTicketLogs = navigateToTicketLogs,
        )
    }
    composable<Editing> {
        TicketEditingRoute(
            navigateToBack = navigateUp
        )
    }
    composable<TicketLogs> {
        TicketLogsRoute(
            navigateUp = navigateUp
        )
    }
}

fun NavController.navigateToDetailsScreen(id: String) {
    navigate(Details(id))
}

fun NavController.navigateToEditingScreen(id: String) {
    navigate(Editing(id))
}

fun NavController.navigateToTicketLogsScreen(id: String) {
    navigate(TicketLogs(id))
}