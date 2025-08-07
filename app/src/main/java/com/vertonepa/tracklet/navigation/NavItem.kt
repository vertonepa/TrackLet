package com.vertonepa.tracklet.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val imageVector: ImageVector? = null,
    val imageResource: Int? = null,
    val label: @Composable (() -> Unit)? = null,
)
