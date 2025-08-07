package com.vertonepa.tracklet

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.vertonepa.tracklet.navigation.AppNavigationBar
import com.vertonepa.tracklet.navigation.NavigationWrapper

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.primary,
        bottomBar = {
            AppNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavigationWrapper(paddingValues = paddingValues, navController = navController)
    }
}