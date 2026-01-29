package com.vertonepa.tracklet.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vertonepa.tracklet.navigation.graphs.TimecounterDestination
import com.vertonepa.tracklet.navigation.graphs.detailsScreen
import com.vertonepa.tracklet.navigation.graphs.mainScreen
import com.vertonepa.tracklet.navigation.graphs.navigateToDetailsScreen
import com.vertonepa.tracklet.navigation.graphs.navigateToEditingScreen
import com.vertonepa.tracklet.navigation.graphs.navigateToMainScreen
import com.vertonepa.tracklet.navigation.graphs.navigateToTicketLogsScreen
import com.vertonepa.tracklet.timecounter.presentation.TimecounterRoute

@Composable
fun AppNavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = TimecounterDestination
    ) {

        //provisional
        composable<TimecounterDestination> {
            TimecounterRoute()
        }

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
            }
        )

    }
}