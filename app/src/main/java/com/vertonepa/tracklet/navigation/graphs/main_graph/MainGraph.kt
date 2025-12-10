package com.vertonepa.tracklet.navigation.graphs.main_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vertonepa.tracklet.MainNavigation
import kotlinx.serialization.Serializable

@Serializable
data object MainDestinationGraph

fun NavGraphBuilder.mainScreen(
    backToMain: () -> Unit,
    navigateToDetailsScreen: (Int) -> Unit
) {
    composable<MainDestinationGraph> {
        MainNavigation(
            backToMain = backToMain,
            navigateToDetailsScreen = navigateToDetailsScreen
        )
    }
}

fun NavController.navigateToMainScreen() {
    navigate(MainDestinationGraph) {
        popUpTo(0) {
            inclusive = true
        }
    }
}