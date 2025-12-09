package com.vertonepa.tracklet

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vertonepa.tracklet.navigation.Settings
import com.vertonepa.tracklet.navigation.TicketCreation
import com.vertonepa.tracklet.navigation.Tickets
import com.vertonepa.tracklet.navigation.creationScreen
import com.vertonepa.tracklet.navigation.navigateToSettings
import com.vertonepa.tracklet.navigation.navigateToTicketCreation
import com.vertonepa.tracklet.navigation.navigateToTicketListScreen
import com.vertonepa.tracklet.navigation.settingsScreen
import com.vertonepa.tracklet.navigation.ticketListScreen
import com.vertonepa.tracklet.ui.theme.TrackletTheme

@Composable
fun MainNavigation(
    backToMain: () -> Unit,
    navigateToDetailsScreen: (String) -> Unit,
    navigateUp: () -> Unit
) {
    val navController = rememberNavController()
    val hierarchy = navController.currentBackStackEntryAsState().value?.destination?.hierarchy

    val isCreationScreen = hierarchy?.any { it.hasRoute(TicketCreation::class) } == true

    Scaffold(
        bottomBar = {
            if(!isCreationScreen) {
                MainBottomBar(
                    hierarchy = hierarchy,
                    navToTicketList = { navController.navigateToTicketListScreen() },
                    navToTicketCreation = { navController.navigateToTicketCreation() },
                    navToSettings = { navController.navigateToSettings() })
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = Tickets
        ) {
            ticketListScreen(
                navigateToDetails = navigateToDetailsScreen
            )
            creationScreen(
                navigateUp = navigateUp,
                backToMain = backToMain
            )
            settingsScreen()
        }
    }
}

@Composable
fun MainBottomBar(
    hierarchy: Sequence<NavDestination>?,
    navToTicketList: () -> Unit,
    navToTicketCreation: () -> Unit,
    navToSettings: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(selected = hierarchy?.any { it.hasRoute(Tickets::class) } == true,
            onClick = navToTicketList,
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "home screen") })

        NavigationBarItem(selected = hierarchy?.any { it.hasRoute(TicketCreation::class) } == true,
            onClick = navToTicketCreation,
            icon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "creation screen"
                )
            })

        NavigationBarItem(selected = hierarchy?.any { it.hasRoute(Settings::class) } == true,
            onClick = navToSettings,
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings, contentDescription = "settings screen"
                )
            })
    }
}

@Preview
@Composable
private fun Preview() {
    TrackletTheme {
        MainNavigation(backToMain = {}, navigateToDetailsScreen = {}, navigateUp = {})
    }
}