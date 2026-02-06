package com.vertonepa.tracklet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vertonepa.tracklet.navigation.AppNavigationRoot
import com.vertonepa.tracklet.ui.theme.TrackletTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrackletTheme {
                AppNavigationRoot()
            }
        }
    }
}