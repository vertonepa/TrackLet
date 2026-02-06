package com.vertonepa.tracklet.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.vertonepa.tracklet.navigation.TicketListDestination
import com.vertonepa.tracklet.navigation.ticketListScreen
import com.vertonepa.tracklet.tickets.presentation.details.TicketDetailsRoute
import com.vertonepa.tracklet.tickets.presentation.edit.EditTicketRoute
import com.vertonepa.tracklet.tickets.presentation.ticketlogs.TicketLogsRoute
import com.vertonepa.tracklet.timecounter.presentation.TimecounterRoute
import com.vertonepa.tracklet.timecounter.presentation.utils.TimecounterValues.Companion.DEEP_LINK_TIMECOUNTER
import kotlinx.serialization.Serializable

@Serializable
data object DetailsGraph

@Serializable
data class DetailsDestination(val ticketId: Int)

@Serializable
data class EditTicketDestination(val ticketId: Int)

@Serializable
data class TicketLogsDestination(val ticketId: Int)

@Serializable
data class TimecounterDestination(val timecounterId: Int, val shouldShowDialog: Boolean = false)

fun NavGraphBuilder.detailsGraph(
    navigateUp: () -> Unit,
    navigateToEditing: (Int) -> Unit,
    navigateToTicketLogs: (Int) -> Unit,
    navigateToTimecounter: (Int) -> Unit
) {
    navigation<DetailsGraph>(startDestination = TicketListDestination) {
        ticketListScreen {
            it
        }
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

        composable<TimecounterDestination>(
            deepLinks = listOf(
                navDeepLink<TimecounterDestination>(basePath = DEEP_LINK_TIMECOUNTER)
            )
        ) {
            val args = it.toRoute<TimecounterDestination>()
            TimecounterRoute(
                navigateUp = navigateUp,
                shouldShowDialog = args.shouldShowDialog
            )
        }

        composable<TicketLogsDestination> {
            TicketLogsRoute(navigateUp = navigateUp)
        }
    }
}

fun NavController.navigateToDetailsGraph(ticketId: Int) {
    navigate(DetailsDestination(ticketId))
}

fun NavController.navigateToEditingScreen(ticketId: Int) {
    navigate(EditTicketDestination(ticketId))
}

fun NavController.navigateToTicketLogsScreen(ticketId: Int) {
    navigate(TicketLogsDestination(ticketId))
}

fun NavController.navigateToTimecounterScreen(
    timecounterId: Int,
    shouldShowDialog: Boolean = false
) {
    navigate(TimecounterDestination(timecounterId, shouldShowDialog))
}