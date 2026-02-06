package com.vertonepa.tracklet.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.vertonepa.tracklet.tickets.presentation.creation.TicketCreationRoute
import com.vertonepa.tracklet.tickets.presentation.ticket_list.TicketListRoute
import kotlinx.serialization.Serializable

@Serializable
object CreateTicketDestination

@Serializable
object TicketListDestination

@Serializable
data object SettingsDestination

fun NavGraphBuilder.creationScreen(
    backToMain: () -> Unit
) {
    composable<CreateTicketDestination>(
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(700)
            )
        }
    ) {
        TicketCreationRoute(backToMain = backToMain)
    }
}

fun NavController.navigateToTicketCreation() {
    navigate(CreateTicketDestination)
}



fun NavGraphBuilder.ticketListScreen(
    navigateToDetails: (Int) -> Unit
) {
    composable<TicketListDestination>(
        deepLinks = listOf(
            navDeepLink<TicketListDestination>(
                basePath = "tracklet://tickets"
            )
        )
    ) {
        TicketListRoute(
            navigateToDetails = navigateToDetails
        )
    }
}

fun NavController.navigateToTicketListScreen() {
    navigate(TicketListDestination) {
        popUpTo(graph.id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}



fun NavGraphBuilder.settingsScreen() {
    composable<SettingsDestination> { }
}

fun NavController.navigateToSettings() {
    navigate(SettingsDestination) {
        popUpTo(graph.id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}