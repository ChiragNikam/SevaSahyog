package com.learn.sevasahyog.ngo_home.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.learn.sevasahyog.ngo_home.items.event.presentation.EventScreen
import com.learn.sevasahyog.ngo_home.items.profile.presentation.ProfileScreen
import com.learn.sevasahyog.ngo_home.items.all.presentation.AllScreen

@Composable
fun NgoBottomNavigation(
    navHostController:NavHostController,
    appNavController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = "all") {
        composable("all") {
            AllScreen(navController = navHostController)
        }
        composable("event") {

            EventScreen(navController = navHostController, appNavController = appNavController)
        }
        composable("profile") {
            ProfileScreen(navController = navHostController)
        }
    }
}
