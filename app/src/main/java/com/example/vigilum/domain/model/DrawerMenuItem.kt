package com.example.vigilum.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerMenuItem(
    val label:String,
    val destination:String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNotification: Boolean,
    val badgeCount:Int? = null,

    )