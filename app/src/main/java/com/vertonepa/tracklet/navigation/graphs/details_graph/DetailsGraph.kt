package com.vertonepa.tracklet.navigation.graphs.details_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vertonepa.tracklet.tickets.presentation.details.TicketDetailsRoute
import com.vertonepa.tracklet.tickets.presentation.editing.TicketEditingRoute
import com.vertonepa.tracklet.tickets.presentation.ticketlogs.TicketLogsRoute
import kotlinx.serialization.Serializable

@Serializable
data class Details(val id: Int)

@Serializable
data class Editing(val id: Int)

@Serializable
data class TicketLogs(val id: Int)

fun NavGraphBuilder.detailsScreen(
    navigateToEditing: (Int) -> Unit,
    navigateToTicketLogs: (Int) -> Unit,
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

fun NavController.navigateToDetailsScreen(id: Int) {
    navigate(Details(id))
}

fun NavController.navigateToEditingScreen(id: Int) {
    navigate(Editing(id))
}

fun NavController.navigateToTicketLogsScreen(id: Int) {
    navigate(TicketLogs(id))
}