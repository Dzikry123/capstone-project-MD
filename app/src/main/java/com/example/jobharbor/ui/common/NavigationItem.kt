package com.example.jobharbor.ui.common

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.jobharbor.ui.navgraph.Route

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screen: Route
)