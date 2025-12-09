package com.vertonepa.tracklet

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vertonepa.tracklet.navigation.graphs.details_graph.detailsScreen
import com.vertonepa.tracklet.navigation.graphs.details_graph.navigateToDetailsScreen
import com.vertonepa.tracklet.navigation.graphs.details_graph.navigateToEditingScreen
import com.vertonepa.tracklet.navigation.graphs.details_graph.navigateToTicketLogsScreen
import com.vertonepa.tracklet.navigation.graphs.main_graph.MainGraph
import com.vertonepa.tracklet.navigation.graphs.main_graph.mainScreen
import com.vertonepa.tracklet.navigation.graphs.main_graph.navigateToMainScreen

@Composable
fun AppNavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainGraph
    ) {
        mainScreen(
            backToMain = { navController.navigateToMainScreen() },
            navigateToDetailsScreen = { navController.navigateToDetailsScreen(it) },
            navigateUp = { navController.navigateUp() }
        )

        detailsScreen(
            navigateToEditing = {
                navController.navigateToEditingScreen(it)
            },
            navigateToTicketLogs = {
                navController.navigateToTicketLogsScreen(it)
            },
            navigateUp = {
                navController.navigateUp()
            }
        )

    }
}