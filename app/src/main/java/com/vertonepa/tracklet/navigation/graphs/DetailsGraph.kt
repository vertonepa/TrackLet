package com.vertonepa.tracklet.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vertonepa.tracklet.tickets.presentation.details.TicketDetailsRoute
import com.vertonepa.tracklet.tickets.presentation.edit.EditTicketRoute
import com.vertonepa.tracklet.tickets.presentation.ticketlogs.TicketLogsRoute
import com.vertonepa.tracklet.timecounter.presentation.TimecounterRoute
import kotlinx.serialization.Serializable

@Serializable
data class DetailsDestination(val ticketId: Int)

@Serializable
data class EditTicketDestination(val ticketId: Int)

@Serializable
data class TicketLogsDestination(val ticketId: Int)

@Serializable
data class TimecounterDestination(val timecounterId: Int)

fun NavGraphBuilder.detailsScreen(
    navigateUp: () -> Unit,
    navigateToEditing: (Int) -> Unit,
    navigateToTicketLogs: (Int) -> Unit,
    navigateToTimecounter: (Int) -> Unit
) {
    composable<DetailsDestination> {
        TicketDetailsRoute(
            navigateUp = navigateUp,
            navigateToEditing = navigateToEditing,
            navigateToTicketLogs = navigateToTicketLogs,
            navigateToTimecounter = navigateToTimecounter
        )
    }
    composable<EditTicketDestination> {
        EditTicketRoute(navigateUp = navigateUp)
    }

    composable<TimecounterDestination> {
        TimecounterRoute(navigateUp = navigateUp)
    }

    composable<TicketLogsDestination> {
        TicketLogsRoute(navigateUp = navigateUp)
    }
}

fun NavController.navigateToDetailsScreen(ticketId: Int) {
    navigate(DetailsDestination(ticketId))
}

fun NavController.navigateToEditingScreen(ticketId: Int) {
    navigate(EditTicketDestination(ticketId))
}

fun NavController.navigateToTicketLogsScreen(ticketId: Int) {
    navigate(TicketLogsDestination(ticketId))
}

fun NavController.navigateToTimecounterScreen(timecounterId: Int) {
    navigate(TimecounterDestination(timecounterId))
}