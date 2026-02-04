package com.vertonepa.tracklet.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vertonepa.tracklet.navigation.graphs.MainDestinationGraph
import com.vertonepa.tracklet.navigation.graphs.detailsScreen
import com.vertonepa.tracklet.navigation.graphs.mainScreen
import com.vertonepa.tracklet.navigation.graphs.navigateToDetailsScreen
import com.vertonepa.tracklet.navigation.graphs.navigateToEditingScreen
import com.vertonepa.tracklet.navigation.graphs.navigateToMainScreen
import com.vertonepa.tracklet.navigation.graphs.navigateToTicketLogsScreen
import com.vertonepa.tracklet.navigation.graphs.navigateToTimecounterScreen

@Composable
fun AppNavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainDestinationGraph
    ) {

        mainScreen(
            backToMain = { navController.navigateToMainScreen() },
            navigateToDetailsScreen = { navController.navigateToDetailsScreen(it) }
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
            },
            navigateToTimecounter = {
                navController.navigateToTimecounterScreen(it)
            }
        )

    }
}