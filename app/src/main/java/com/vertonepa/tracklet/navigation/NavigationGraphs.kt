package com.vertonepa.tracklet.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vertonepa.tracklet.tickets.presentation.creation.TicketCreationRoute
import com.vertonepa.tracklet.tickets.presentation.ticket_list.TicketListRoute
import kotlinx.serialization.Serializable

@Serializable
object TicketCreation

fun NavGraphBuilder.creationScreen(
    backToMain: () -> Unit
) {
    composable<TicketCreation> {
        TicketCreationRoute(backToMain = backToMain)
    }
}

fun NavController.navigateToTicketCreation() {
    navigate(TicketCreation)
}

@Serializable
object Tickets

fun NavGraphBuilder.ticketListScreen(
    navigateToDetails: (Int) -> Unit
) {
    composable<Tickets> {
        TicketListRoute(
            navigateToDetails = navigateToDetails
        )
    }
}

fun NavController.navigateToTicketListScreen() {
    navigate(Tickets) {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}

@Serializable
data object Settings

fun NavGraphBuilder.settingsScreen() {
    composable<Settings> { }
}

fun NavController.navigateToSettings() {
    navigate(Settings)
}