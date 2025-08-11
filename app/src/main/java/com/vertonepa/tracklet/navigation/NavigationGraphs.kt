package com.vertonepa.tracklet.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vertonepa.tracklet.tickets.presentation.details.TicketDetailsRoute
import com.vertonepa.tracklet.tickets.presentation.ticket_list.TicketListRoute
import kotlinx.serialization.Serializable

@Serializable
object HomeGraph

@Serializable
object TicketsGraph

@Serializable
object NotificationsGraph

@Serializable
object SettingsGraph

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation<HomeGraph>(startDestination = Home) {
        composable<Home> { }
    }
}

fun NavGraphBuilder.ticketsGraph(navController: NavHostController) {
    navigation<TicketsGraph>(startDestination = Tickets) {
        composable<Tickets> {
            TicketListRoute(
                navigateToDetails = { id -> navController.navigate(Details(id = id)) }
            )
        }

        composable<Details> {
            TicketDetailsRoute(
                navigateToBack = { navController.popBackStack() }
            )
        }
    }
}

fun NavGraphBuilder.notificationsGraph(navController: NavHostController) {
    navigation<NotificationsGraph>(startDestination = Notifications) {
        composable<Notifications> { }
    }
}

fun NavGraphBuilder.settingsGraph(navController: NavHostController) {
    navigation<SettingsGraph>(startDestination = Settings) {
        composable<Settings> { }
    }
}