package com.vertonepa.tracklet.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.vertonepa.tracklet.ui.theme.TrackletTheme

@Composable
fun MainNavigation(
    backToMain: () -> Unit,
    navigateToDetailsScreen: (Int) -> Unit
) {
    val navController = rememberNavController()
    val hierarchy = navController.currentBackStackEntryAsState().value?.destination?.hierarchy

    val isCreationScreenVisible =
        hierarchy?.any { it.hasRoute(CreateTicketDestination::class) } == true

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = !isCreationScreenVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                MainBottomBar(
                    hierarchy = hierarchy,
                    navToTicketList = { navController.navigateToTicketListScreen() },
                    navToTicketCreation = { navController.navigateToTicketCreation() },
                    navToSettings = { navController.navigateToSettings() }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = TicketListDestination
        ) {
            ticketListScreen(navigateToDetails = navigateToDetailsScreen)
            creationScreen(backToMain = backToMain)
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
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        NavigationBarItem(selected = hierarchy?.any { it.hasRoute(TicketListDestination::class) } == true,
            onClick = navToTicketList,
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "home screen") })

        NavigationBarItem(selected = hierarchy?.any { it.hasRoute(CreateTicketDestination::class) } == true,
            onClick = navToTicketCreation,
            icon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "creation screen"
                )
            })

        NavigationBarItem(selected = hierarchy?.any { it.hasRoute(SettingsDestination::class) } == true,
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
        MainNavigation(
            backToMain = {},
            navigateToDetailsScreen = {}
        )
    }
}