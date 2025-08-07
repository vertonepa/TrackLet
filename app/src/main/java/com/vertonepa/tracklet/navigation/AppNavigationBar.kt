package com.vertonepa.tracklet.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigationBar(navController: NavHostController) {

    NavigationBar {
        val entry by navController.currentBackStackEntryAsState()
        val currentDestination = entry?.destination

        val items = mapOf(
            HomeGraph to NavItem(
                imageVector = Icons.Default.Home,
                label = { Text(text = "Actividad") }),
            TicketsGraph to NavItem(
                imageVector = Icons.Default.Menu,
                label = { Text(text = "Tickets") }),
            TicketCreation to NavItem(imageVector = Icons.Default.Add),
            NotificationsGraph to NavItem(imageVector = Icons.Default.Notifications, label = {Text(text = "Notificados")}),
            SettingsGraph to NavItem(
                imageVector = Icons.Default.Settings,
                label = { Text(text = "Opciones") }),
        )

        items.forEach { (graph, navItem) ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(graph::class)
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(graph) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    if (navItem.imageVector != null) {
                        Icon(
                            imageVector = navItem.imageVector,
                            contentDescription = null
                        )
                    }
                    if (navItem.imageResource != null) {
                        Icon(
                            painter = painterResource(navItem.imageResource),
                            contentDescription = null
                        )
                    }
                },
                label = navItem.label
            )
        }

    }

}

@Preview
@Composable
private fun Preview() {
    AppNavigationBar(navController = rememberNavController())
}