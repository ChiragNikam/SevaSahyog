package com.learn.sevasahyog.ngo_home.data

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: Painter,
    val unselectedIcon: Painter
)