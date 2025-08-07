package com.vertonepa.tracklet.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vertonepa.tracklet.tickets.presentation.creation.TicketCreationRoute


@Composable
fun NavigationWrapper(paddingValues: PaddingValues, navController: NavHostController) {
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        startDestination = TicketsGraph
    ) {
        homeGraph(navController)
        ticketsGraph(navController)
        composable<TicketCreation> {
            TicketCreationRoute(
                navigateBack = { navController.popBackStack() },
                navigateToTicketListScreen = { navController.navigate(Tickets) }
            )
        }
        notificationsGraph(navController)
        settingsGraph(navController)
    }
}