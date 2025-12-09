package com.vertonepa.tracklet.navigation.graphs.main_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vertonepa.tracklet.MainNavigation
import kotlinx.serialization.Serializable

@Serializable
data object MainGraph

fun NavGraphBuilder.mainScreen(
    backToMain: () -> Unit,
    navigateToDetailsScreen: (String) -> Unit,
    navigateUp: () -> Unit
) {
    composable<MainGraph> {
        MainNavigation(
            backToMain = backToMain,
            navigateToDetailsScreen = navigateToDetailsScreen,
            navigateUp = navigateUp
        )
    }
}

fun NavController.navigateToMainScreen() {
    navigate(MainGraph) {
        popUpTo(0) {
            inclusive = true
        }
    }
}